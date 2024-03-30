package az.edu.bhos.l14todoapp.flows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.edu.bhos.l14todoapp.data.TodoRepository
import az.edu.bhos.l14todoapp.entities.TodoBundle
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    todoRepo: TodoRepository
) : ViewModel() {

    private val _todoBundles: MutableLiveData<List<TodoBundle>> = MutableLiveData()

    val todoBundles: LiveData<List<TodoBundle>>
        get() = _todoBundles

    init {
        viewModelScope.launch {
            todoRepo.syncTodos()
        }

        todoRepo.observeTodoEntries()
            .onEach { todos ->
                val groupedByWeekday = todos.groupBy { it.weekday }
                val todoBundles = groupedByWeekday.map { (weekday, todoList) ->
                    TodoBundle(weekday, todoList)
                }
                _todoBundles.postValue(todoBundles)
            }.launchIn(viewModelScope)
    }
}