package org.example.utils

/**
 * Добавляет к строкам управляющие символы ascii, позволяющие окрашивать текст в разные цвета.
 */
class StringColorizer {
    companion object {
        private const val colorReset = "\u001b[0m"

        /**
         * Окрасить строку в красный цвет.
         * @param string строка.
         */
        fun toRed(string: String) = "\u001b[31m$string$colorReset"

        /**
         * Окрасить строку в желтый цвет.
         * @param string строка.
         */
        fun toYellow(string: String) = "\u001b[33m$string$colorReset"

        /**
         * Окрасить строку в зеленый цвет.
         * @param string строка.
         */
        fun toGreen(string: String) = "\u001b[32m$string$colorReset"

        /**
         * Окрасить строку в синий цвет.
         * @param string строка.
         */
        fun toBlue(string: String) = "\u001b[34m$string$colorReset"

        /**
         * Окрасить строку в белый цвет.
         * @param string строка.
         */
        fun toWhite(string: String) = "\u001b[37m$string$colorReset"

        /**
         * Окрасить строку в фиолетовый цвет.
         * @param string строка.
         */
        fun toPurple(string: String) = "\u001b[35m$string$colorReset"
    }
}