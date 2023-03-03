package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.db.AppDatabase
import com.example.todolist.db.ToDoDao
import com.example.todolist.db.ToDoEntity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    
    private lateinit var db : AppDatabase
    private lateinit var todoDao : ToDoDao
    private lateinit var todoList : ArrayList<ToDoEntity>
    private lateinit var adapter : TodoRecycleViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }
        
        // DB 인스턴스를 가져오고 DB 작업 을 할 수 있는 DAO 를 가져옵니다.
        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()
        
        getAllTodoList()    /// 할 일 리스트 가져오기
    }
    
    private fun getAllTodoList() {
        Thread {
            todoList = ArrayList(todoDao.getAll())
            setRecyclerView()
        }.start()
    }
    
    private fun setRecyclerView() {
        // 리사이클러뷰 설정
        runOnUiThread {
            adapter = TodoRecycleViewAdapter(todoList)  // 어댑터 객체 할당
            binding.recyclerView.adapter = adapter
            // 리사이클러뷰 어댑터로 위에서 만든 어댑터 설정
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            // 레이아웃 매니저 설정
        }
    }

    override fun onRestart() {
        super.onRestart()
        getAllTodoList()
    }
}