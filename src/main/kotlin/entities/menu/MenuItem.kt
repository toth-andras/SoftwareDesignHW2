package org.example.entities.menu

import kotlinx.serialization.Serializable

/**
 * Представляет одно блюдо из меню.
 * @param id идентификатор блюда.
 * @param name название блюда.
 * @param quantity количество порций блюда.
 * @param price цена за одну порцию блюда.
 * @param timeToCook время преготовления одной порции блюда.
 */
@Serializable
class MenuItem(val id: Int, var name: String, var quantity: Int, var price: Int, var timeToCook: Int) {}