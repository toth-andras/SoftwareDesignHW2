package org.example.entities.menu

/**
 * Вызыается при попытке добавить в заказ больше порций блюда, чем есть в наличии.
 */
class NotEnoughPortionsException(portionsRequested: Int, portionsAvailable: Int)
    : Exception("Нет такого количества порций блюда. Заказано: $portionsRequested, в наличии: $portionsAvailable") {}