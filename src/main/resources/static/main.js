'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var roomPage = document.createElement('div');
var roomForm = null;
var roomIdInput = null;
var roomId = null;

window.addEventListener('DOMContentLoaded', function() {
    roomPage = document.querySelector('#room-page');
    roomForm = document.querySelector('#roomForm');
    roomIdInput = document.querySelector('#roomId');
    roomPage.classList.remove('hidden');
    usernamePage.classList.add('hidden');
    chatPage.classList.add('hidden');
    roomForm.addEventListener('submit', enterRoom, true);
});

function enterRoom(event) {
    roomId = roomIdInput.value.trim();
    if(roomId) {
        // Gọi API tạo/join phòng
        fetch('/room/' + roomId, {method: 'POST'})
            .then(res => {
                // Sau khi join phòng, lấy tin nhắn cũ
                fetch(`/room/${roomId}/messages?page=0&size=50`)
                    .then(response => response.json())
                    .then(messages => {
                        messageArea.innerHTML = '';
                        // Hiển thị tin nhắn cũ
                        messages.forEach(function(message) {
                            renderMessage(message);
                        });
                    });
                roomPage.classList.add('hidden');
                usernamePage.classList.remove('hidden');
            });
    }
    event.preventDefault();
}

function renderMessage(message) {
    var messageElement = document.createElement('li');
    if(message.messageType === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.messageType === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');
        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(
            message.sender[0]
        );
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);
        messageElement.appendChild(avatarElement);
        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }
    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);
    messageElement.appendChild(textElement);
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();
    if(username && roomId) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    // Subscribe vào topic của phòng
    stompClient.subscribe('/topic/room/' + roomId, onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/addUser",
        {},
        JSON.stringify({sender: username, messageType: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient && roomId) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            messageType: 'CHAT'
        };
        stompClient.send("/app/sendMessage/" + roomId, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    renderMessage(message);
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)