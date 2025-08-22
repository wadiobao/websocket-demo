package com.project.library_management.enums;

public enum Language {
    ENGLISH("English", "en"),
    VIETNAMESE("Tiếng Việt", "vi"),
    JAPANESE("日本語", "ja"),
    KOREAN("한국어", "ko"),
    CHINESE("中文", "zh"),
    FRENCH("Français", "fr");

    private final String displayName;
    private final String code;

    Language(String displayName, String code) {
        this.displayName = displayName;
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCode() {
        return code;
    }

    // Tìm Language theo code (ví dụ "vi" → VIETNAMESE)
    public static Language fromCode(String code) {
        for (Language lang : Language.values()) {
            if (lang.getCode().equalsIgnoreCase(code)) {
                return lang;
            }
        }
        throw new IllegalArgumentException("Unsupported language code: " + code);
    }
}
