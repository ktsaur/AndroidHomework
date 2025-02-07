package ru.itis.homework6.screens

import android.os.Bundle
import android.util.Log
import android.view.View
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
import ru.itis.homework6.ui.MainText
import ru.itis.homework6.ui.ProfileButton
import ru.itis.homework6.ui.SongsList
import java.util.UUID

class MainFragment: BaseFragment(R.layout.fragment_main) {
    private val userRepository = ServiceLocator.getUserRepository()

    private val viewBinding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)

    private var list by mutableStateOf<List<SongEntity>>(emptyList())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getString("USER_ID") ?: throw IllegalStateException("UserId is null")
        Log.i("MainFragment--TAG", "userId = $userId")

        getAllSongs()
        initViews()
    }

    fun initViews() {
        viewBinding.composeContainerId.setContent {
            Scaffold { padding ->
                Column {
                    Row (modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        MainText()
                        ProfileButton { profile() }
                        AddSongButton { addSong() }
                    }
//                    MainText()
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
        val userId = arguments?.getString("USER_ID") ?: throw IllegalStateException("UserId is null")
        Log.i("MainFragment--TAG", "userId = $userId")

        lifecycleScope.launch {
            list = userRepository.getAllUserSongs(user_id = userId)
            Log.i("MainFragment--TAG", "list is empty? - ${list.isEmpty()}")
        }
    }

    fun addSong() {
        val userId = arguments?.getString("USER_ID") ?: throw IllegalStateException("UserId is null")

        val addContentFragment = AddContentFragment().apply {
            arguments = AddContentFragment().bindle(userId)
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, addContentFragment)
            .addToBackStack(null)
            .commit()
    }

    fun profile() {
        val userId = arguments?.getString("USER_ID") ?: throw IllegalStateException("UserId is null")

        val profileFragment = ProfileFragment().apply {
            arguments = ProfileFragment().bundle(
                userId = userId
            )
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.container, profileFragment)
            .addToBackStack(null)
            .commit()
    }

    fun bundle(userId: String): Bundle = Bundle().apply {
        putString("USER_ID", userId)
    }
}