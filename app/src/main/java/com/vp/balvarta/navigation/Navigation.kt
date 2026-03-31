package com.vp.balvarta.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vp.balvarta.screens.HomeScreen
import com.vp.balvarta.screens.StoryDetailsScreen
import com.vp.balvarta.screens.SplashScreen

@Composable
fun GujaratiStoriesNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }
        
        composable("home") {
            HomeScreen(
                onStoryClick = { storyId ->
                    navController.navigate("story_details/$storyId")
                }
            )
        }
        
        composable("story_details/{storyId}") { backStackEntry ->
            val storyId = backStackEntry.arguments?.getString("storyId")?.toIntOrNull() ?: 1
            StoryDetailsScreen(
                storyId = storyId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}