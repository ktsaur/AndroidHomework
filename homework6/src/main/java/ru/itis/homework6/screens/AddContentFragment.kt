package ru.itis.homework6.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import ru.itis.homework6.R
import ru.itis.homework6.data.db.entities.SongEntity
import ru.itis.homework6.databinding.FragmentAddContentBinding
import ru.itis.homework6.di.ServiceLocator
import ru.itis.homework6.ui.AddContentText
import ru.itis.homework6.ui.AddContentTextField
import ru.itis.homework6.ui.AddNewSongButton
import ru.itis.homework6.util.PreferencesKeys
import java.util.UUID


class AddContentFragment: BaseFragment(R.layout.fragment_add_content) {
    private val userRepository = ServiceLocator.getUserRepository()

    private val viewBinding: FragmentAddContentBinding by viewBinding(FragmentAddContentBinding::bind)

    private var title by mutableStateOf("")
    private var singer by mutableStateOf("")
    private var composer by mutableStateOf("")
    private var genre by mutableStateOf("")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()
    }

    fun setView() {
        viewBinding.composeContainerId.setContent {
            Scaffold { padding ->
               Column(modifier = Modifier.fillMaxSize(),
                   horizontalAlignment = Alignment.CenterHorizontally) {
                   AddContentText()
                   AddContentTextField(value = title,
                       onValueChange = {newValue ->
                           title = newValue
                       },
                       label = getString(R.string.title))
                   AddContentTextField(value = singer,
                       onValueChange = {newValue ->
                           singer = newValue
                       },
                       label = getString(R.string.singer))
                   AddContentTextField(value = composer,
                       onValueChange = {newValue ->
                           composer = newValue
                       },
                       label = getString(R.string.composer))
                   AddContentTextField(value = genre,
                       onValueChange = {newValue ->
                           genre = newValue
                       },
                       label = getString(R.string.genre))
                   AddNewSongButton {addSong()}
               }
            }
        }
    }

    fun addSong() {
        val userId = getUserId() ?: throw IllegalStateException(context?.getString(R.string.userId_is_null))
        if (title.isEmpty() ||  singer.isEmpty() || composer.isEmpty() ||  genre.isEmpty()) {
            Toast.makeText(context, context?.getString(R.string.fields_filled), Toast.LENGTH_SHORT).show()
            return
        }
        lifecycleScope.launch {
            val song = SongEntity(
                songId = UUID.randomUUID().toString(),
                userId = userId,
                title = title,
                singer = singer,
                composer = composer,
                genre = genre
            )
            userRepository.saveSong(song)
            Toast.makeText(context, context?.getString(R.string.song_added), Toast.LENGTH_SHORT).show()

            parentFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .addToBackStack(null)
                .commit()
        }
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