/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.TodoItem;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.session.FlashScope;
import repository.TodoItemRepository;

import java.util.List;


@Singleton
public class ApplicationController {

    @Inject
    private TodoItemRepository todoItemRepository;

    // ==== Public API ====

    public Result index() {
        return getToDoList();
    }

    public Result addToDoItem(FlashScope flashScope,
                              @Param("newTodoItem") String newTodoItem) {

        if (validateTodoItem(flashScope, newTodoItem)) {
            TodoItem todoItem = TodoItem.create(newTodoItem);
            todoItemRepository.persist(todoItem);
        }

        return getToDoList();
    }

    public Result updateToDoItem(FlashScope flashScope,
                                 @Param("pk") Long updateItemId,
                                 @Param("value") String updatedItemText) {

        if (validateTodoItem(flashScope, updatedItemText) && updateItemId != null) {
            TodoItem todoItem = todoItemRepository.getById(updateItemId);
            todoItem.setTodoItem(updatedItemText);
            todoItemRepository.update(todoItem);
        }

        return getToDoList();
    }

    public Result removeToDoItem(@Param("removeItemId") Long removeItemId) {
        if (removeItemId != null) {
            todoItemRepository.delete(removeItemId);
        }

        return getToDoList();
    }

    // ==== Private Helper Methods ====

    private Result getToDoList() {
        List<TodoItem> todoItems = todoItemRepository.listAll();

        return Results
                .html()
                .template("views/ApplicationController/index.ftl.html")
                .render("todoitems", todoItems);
    }

    private boolean validateTodoItem(FlashScope flashScope, String todoText) {
        if (Strings.isNullOrEmpty(todoText)) {
            flashScope.error("The todo item cannot be empty - please add some text.");
            return false;
        }
        return true;
    }
}
