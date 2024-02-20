package org.example.entities.menu

import kotlinx.serialization.Serializable

/**
 * Представляет одно блюдо из меню.
 * @param id идентификатор блюда.
 * @param name название блюда.
 * @param _quantity количество порций блюда.
 * @param price цена за одну порцию блюда.
 * @param timeToCook время преготовления одной порции блюда.
 */
@Serializable
class MenuItem(var id: Int, var name: String, private var _quantity: Int, var price: Int, var timeToCook: Int) {
    constructor(name: String, quantity: Int, price: Int, timeToCook: Int): this(0, name, quantity, price, timeToCook)

    init {
        require(name.trim().isNotBlank()) {"Название блюда не может быть пустой строкой"}
        require(_quantity >= 0) {"Количество порций блюда не может быть отрицательным. Текущее значение: $_quantity"}
        require(price > 0) {"Цена за блюдо должна быть положительной. Текущее значение: $price"}
        require(timeToCook >= 0) {"Время приготовлении порции не может быть отрицательным. Текущее значение: $timeToCook"}
    }

    var quantity: Int
        get() = _quantity
        set(value) {
            require(value >= 0) {"Количество порций блюда не может быть отрицательным. Текущее значение: $value"}
            _quantity = value
        }
}