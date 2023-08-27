package com.pkg.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pkg.noteapp.presentation.noteDetail.NoteDetailScreen
import com.pkg.noteapp.presentation.noteList.NoteListScreen
import com.pkg.noteapp.ui.theme.NoteAppTheme
import com.pkg.noteapp.util.Constants
import com.pkg.noteapp.util.Constants.KEY_NOTE_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NoteAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Constants.ROUTE_HOME
                    ) {
                        composable(route = Constants.ROUTE_HOME) {
                            NoteListScreen(onGotoDetailAction = { noteId ->
                                navController.navigate(Constants.ROUTE_ADD_EDIT_NOTE + noteId)
                            })
                        }
                        composable(
                            route = Constants.ROUTE_ADD_EDIT_NOTE + "{noteId}",
                            arguments = listOf(
                                navArgument(KEY_NOTE_ID) {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            NoteDetailScreen()
                        }
                    }

                }
            }
        }
    }
}
