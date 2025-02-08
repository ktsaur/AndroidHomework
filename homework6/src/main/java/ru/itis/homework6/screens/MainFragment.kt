package ru.itis.homework6.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import ru.itis.homework6.R
import ru.itis.homework6.data.db.entities.SongEntity
import ru.itis.homework6.databinding.FragmentMainBinding
import ru.itis.homework6.di.ServiceLocator
import ru.itis.homework6.ui.Button
import ru.itis.homework6.ui.MainText
import ru.itis.homework6.ui.SongsList
import ru.itis.homework6.util.PreferencesKeys

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
                Column {
                    Row (modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ){
                        MainText()
                        Spacer(modifier = Modifier.width(40.dp))
                        Button ({profile()}, text = getString(R.string.profile) )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button ({addSong()}, text = getString(R.string.add_song) )
                    }
                    Column {
                        val listState = rememberLazyListState()
                        for(song in list) {
                            Log.i("SongsInInitViews--TAG", "${song.title}")
                        }
                        SongsList(listState = listState,
                            items = list,
                            onSongLongPress = { song -> removeSong(song) }
                        )
                    }
                }
            }
        }
    }
    fun getAllSongs(){
        val userId = getUserId() ?: throw IllegalStateException(context?.getString(R.string.userId_is_null))
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
            Toast.makeText(context, context?.getString(R.string.song_deleted), Toast.LENGTH_SHORT).show()

            getAllSongs()
        }
    }

    fun profile() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, ProfileFragment())
            .addToBackStack(null)
            .commit()
    }

    fun getUserId(): String?{
        val sharedPreference = requireContext().getSharedPreferences(PreferencesKeys.USER_PREFS,  android.content.Context.MODE_PRIVATE)
        return sharedPreference.getString(PreferencesKeys.USER_ID, null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        composeView = null
    }

}