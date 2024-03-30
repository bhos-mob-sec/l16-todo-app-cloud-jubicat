package az.edu.bhos.l14todoapp.data

import az.edu.bhos.l14todoapp.data.dto.TodoRemoteDto
import az.edu.bhos.l14todoapp.entities.TodoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface TodoLocalData {
    fun observeTodoItems(): Flow<List<TodoEntity>>
    suspend fun save(todoEntity: TodoEntity)
}

class TodoLocalDataImpl : TodoLocalData {

    private val _todoEntities = MutableStateFlow<List<TodoEntity>>(emptyList())

    init {
        _todoEntities.value = listOf()
    }

    override fun observeTodoItems(): Flow<List<TodoEntity>> {
        return _todoEntities.asStateFlow()
    }

    override suspend fun save(todoEntity: TodoEntity) {
        _todoEntities.value += todoEntity
    }

}