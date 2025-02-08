package ru.itis.homework6.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import ru.itis.homework6.R
import ru.itis.homework6.data.db.entities.SongEntity
import ru.itis.homework6.databinding.FragmentMainBinding
import ru.itis.homework6.di.ServiceLocator
import ru.itis.homework6.ui.AddSongButton
import ru.itis.homework6.ui.BottomSheetDemo
import ru.itis.homework6.ui.LogoutButton
import ru.itis.homework6.ui.MainText
import ru.itis.homework6.ui.SongsList

class MainFragment: BaseFragment(R.layout.fragment_main) {
    private val userRepository = ServiceLocator.getUserRepository()

    private val viewBinding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)

    private var list by mutableStateOf<List<SongEntity>>(emptyList())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllSongs()
        initViews()
    }

    fun initViews() {
        viewBinding.composeContainerId.setContent {
            Scaffold { padding ->
                BottomSheetDemo(onDelete = { delete() },
                    onLogOut = {logout()})
                Column {
                    Row (modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        MainText()
//                        LogoutButton { logout() }
                        AddSongButton { addSong() }
                    }
                    Column {
                        val listState = rememberLazyListState()
                        for(song in list) {
                            Log.i("SongsInInitViews--TAG", "${song.title}")
                        }
                        SongsList(listState = listState, items = list)
                    }
                }
            }
        }
    }
    fun getAllSongs(){
        val userId = getUserId() ?: throw IllegalStateException("UserId is null")
        lifecycleScope.launch {
            list = userRepository.getAllUserSongs(user_id = userId)
            Log.i("MainFragment--TAG", "list is empty? - ${list.isEmpty()}")
        }
    }

    fun addSong() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, AddContentFragment())
            .addToBackStack(null)
            .commit()
    }

    fun removeSong(song: SongEntity) {
        lifecycleScope.launch {
            userRepository.deleteSong(song = song)
            Toast.makeText(context, "Song deleted!", Toast.LENGTH_SHORT).show()
        }
    }

    fun logout(){
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", android.content.Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        parentFragmentManager.beginTransaction()
            .replace(R.id.container, AuthorizationFragment())
            .addToBackStack(null)
            .commit()
    }

    fun delete() {
        val userId = getUserId() ?: throw IllegalStateException("UserId is null")
        lifecycleScope.launch {
            val user = userRepository.getUserById(userId)
            if (user != null) {
                userRepository.deleteUser(user = user)
                Toast.makeText(context, "User ${user.username} deleted.", Toast.LENGTH_SHORT).show()
            }
        }

        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", android.content.Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        parentFragmentManager.beginTransaction()
            .replace(R.id.container, AuthorizationFragment())
            .addToBackStack(null)
            .commit()
    }

    fun getUserId(): String?{
        val sharedPreference = requireContext().getSharedPreferences("user_prefs",  android.content.Context.MODE_PRIVATE)
        return sharedPreference.getString("user_id", null)
    }

}