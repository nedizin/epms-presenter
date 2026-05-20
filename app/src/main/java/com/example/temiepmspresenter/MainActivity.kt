package com.example.temiepmspresenter

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.temiepmspresenter.data.Course
import com.example.temiepmspresenter.data.CourseRepository
import com.example.temiepmspresenter.ui.screens.CourseDetailScreen
import com.example.temiepmspresenter.ui.screens.CourseFinderScreen
import com.example.temiepmspresenter.ui.screens.CreditsScreen
import com.example.temiepmspresenter.ui.screens.MainScreen
import com.example.temiepmspresenter.ui.theme.TemiEPMSPresenterTheme

private sealed class Screen {
    object Main : Screen()
    data class Detail(val course: Course) : Screen()
    object Credits : Screen()
    object CourseFinder : Screen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }
            TemiEPMSPresenterTheme(darkTheme = isDarkTheme) {
                var screen by remember { mutableStateOf<Screen>(Screen.Main) }

                BackHandler(enabled = screen != Screen.Main) {
                    screen = Screen.Main
                }

                AnimatedContent(
                    targetState = screen,
                    transitionSpec = {
                        if (targetState !is Screen.Main) {
                            (slideInHorizontally(
                                animationSpec = tween(380),
                                initialOffsetX = { it }
                            ) + fadeIn(tween(300))).togetherWith(
                                slideOutHorizontally(
                                    animationSpec = tween(300),
                                    targetOffsetX = { -it / 4 }
                                ) + fadeOut(tween(200))
                            )
                        } else {
                            (slideInHorizontally(
                                animationSpec = tween(380),
                                initialOffsetX = { -it }
                            ) + fadeIn(tween(300))).togetherWith(
                                slideOutHorizontally(
                                    animationSpec = tween(300),
                                    targetOffsetX = { it / 4 }
                                ) + fadeOut(tween(200))
                            )
                        }
                    },
                    label = "screenTransition"
                ) { currentScreen ->
                    when (currentScreen) {
                        is Screen.Main -> MainScreen(
                            courses = CourseRepository.courses,
                            onCourseSelected = { screen = Screen.Detail(it) },
                            onCreditsClick = { screen = Screen.Credits },
                            onNavigateToCourseFinder = { screen = Screen.CourseFinder },
                            isDarkTheme = isDarkTheme,
                            onToggleTheme = { isDarkTheme = !isDarkTheme }
                        )
                        is Screen.Detail -> CourseDetailScreen(
                            course = currentScreen.course,
                            onBack = { screen = Screen.Main }
                        )
                        is Screen.Credits -> CreditsScreen(
                            onBack = { screen = Screen.Main }
                        )
                        is Screen.CourseFinder -> CourseFinderScreen(
                            courses = CourseRepository.courses,
                            onBack = { screen = Screen.Main },
                            onCourseSelected = { screen = Screen.Detail(it) }
                        )
                    }
                }
            }
        }
    }
}
