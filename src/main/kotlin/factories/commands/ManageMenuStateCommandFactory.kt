package org.example.factories.commands

import org.example.Application
import org.example.commands.Command
import org.example.commands.menu.AddMenuItemCommand
import org.example.commands.menu.EditMenuItemCommand
import org.example.commands.menu.RemoveMenuItemCommand
import org.example.commands.navigation.BackCommand

class ManageMenuStateCommandFactory: CommandFactory<Application> {
    override fun createCommandSet(): Iterable<Command<Application>> {
        return listOf(AddMenuItemCommand(), EditMenuItemCommand(), RemoveMenuItemCommand(), BackCommand())
    }
}