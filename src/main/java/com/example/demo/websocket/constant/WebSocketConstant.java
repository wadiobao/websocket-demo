package com.example.demo.websocket.constant;

public class WebSocketConstant {

    public static class WebSocketEventListenerConstants {
        public static final String USERNAME = "username";
        public static final String TOPIC_PUBLIC = "/topic/public";
    }

    public static class WebConfigConstants {
        public static final String ALLOWED_ORIGINS = "http://localhost:3000";
        public static final String ALLOWED_METHODS = "GET,POST,PUT,DELETE,OPTIONS";
        public static final String ALLOWED_HEADERS = "*";
        public static final String MAPPING = "/**";
    }

    public static class WebSocketConfigConstants {
        public static final String ENDPOINT = "/ws";
        public static final String APP_PREFIX = "/app";
        public static final String TOPIC = "/topic";
    }

    public static class ChatControllerConstants {
        public static final String SEND_MESSAGE_ENDPOINT = "/sendMessage/{roomId}";
        public static final String TOPIC_ROOM = "/topic/room/{roomId}";
        public static final String ADD_USER_ENDPOINT = "/addUser";
        public static final String SEND_MESSAGE_TO_PUBLIC_ENDPOINT = "/sendMessageToPublic";
    }

    public static class RoomControllerConstants {
        public static final String ROOM_ENDPOINT = "/room";
        public static final String CREATE_ROOM_ENDPOINT = "/create";
        public static final String JOIN_ROOM_ENDPOINT = "/join";
        public static final String MESSAGES_ENDPOINT = "/{roomId}/messages";
        public static final String ALL_ROOMS_ENDPOINT = "/all";
        public static final String ROOM_ID_PATH_VARIABLE = "roomId";
        public static final String PAGE_DEFAULT_VALUE = "0";
        public static final String SIZE_DEFAULT_VALUE = "20";
    }

    public static class RoomServiceConstants {
        public static final String ROOM_NOT_FOUND = "Room not found";
        public static final String SERVER_KEY = "QeGdsM26H0ertGcX";
        public static final String DEMO_USER = "demo2test";
    }
    
    public static class TextHandlerConstants {
        public static final String ROOM_ID_PREFIX = "room_of_";
        public static final String ROOM_ID_SEPARATOR = "_and_";
        public static final String INVALID_ROOM_ID_FORMAT = "Invalid room ID format";
        public static final String INVALID_ROOM_ID_FORMAT_EXPECTING_TWO_USERNAMES = "Invalid room ID format (expecting two usernames)";
    }
}