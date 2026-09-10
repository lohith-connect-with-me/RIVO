package com.example.cooperativegig.presentation.customer

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.cooperativegig.presentation.auth.AuthUiState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cooperativegig.presentation.auth.AuthViewModel
import com.example.cooperativegig.presentation.customer.booking.BookingHistoryScreen
import com.example.cooperativegig.presentation.customer.home.CustomerHomeScreen
import com.example.cooperativegig.presentation.customer.profile.CustomerProfileScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp

sealed class CustomerBottomNavItem(val route: String, val title: String, val icon: @Composable () -> Unit) {
    object Home : CustomerBottomNavItem("cust_home", "Home", { Icon(Icons.Default.Home, contentDescription = "Home") })
    object Bookings : CustomerBottomNavItem("cust_bookings", "Bookings", { Icon(Icons.Default.DateRange, contentDescription = "Bookings") })
    object Profile : CustomerBottomNavItem("cust_profile", "Profile", { Icon(Icons.Default.Person, contentDescription = "Profile") })
}

@Composable
fun CustomerMainScreen(
    authViewModel: AuthViewModel,
    isGuest: Boolean = false,
    onNavigateToServiceDetail: (Long) -> Unit,
    onNavigateToWorkerDetail: (String) -> Unit = {},
    onNavigateToEmergencyBooking: () -> Unit,
    onNavigateToSelectLocation: () -> Unit = {},
    onNavigateToNearbyMap: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToEditProfile: () -> Unit = {},
    onNavigateToSavedAddresses: () -> Unit = {},
    onNavigateToSavedWorkers: () -> Unit = {},
    onNavigateToCustomerReviews: () -> Unit = {},
    onNavigateToSettingsPreferences: () -> Unit = {},
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    val navItems = listOf(
        CustomerBottomNavItem.Home,
        CustomerBottomNavItem.Bookings,
        CustomerBottomNavItem.Profile
    )
    val authState by authViewModel.uiState.collectAsState()

    val userName = when (val state = authState) {
        is AuthUiState.Authenticated -> {
            state.profile.email ?: "Guest"
        }
        else -> "Guest"
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                navItems.forEach { item ->
                    NavigationBarItem(
                        icon = item.icon,
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CustomerBottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(CustomerBottomNavItem.Home.route) {
                CustomerHomeScreen(
                    userName = userName,
                    onServiceClick = onNavigateToServiceDetail,
                    onEmergencyClick = onNavigateToEmergencyBooking,
                    onWorkerClick = onNavigateToWorkerDetail,
                    onLocationClick = onNavigateToSelectLocation,
                    onNearbyMapClick = onNavigateToNearbyMap,
                    onNotificationClick = onNavigateToNotifications,
                    onProfileClick = {
                        navController.navigate(CustomerBottomNavItem.Profile.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            composable(CustomerBottomNavItem.Bookings.route) {
                BookingHistoryScreen(
                    onBookingClick = { bookingId ->
                        // Navigate to tracking
                    },
                    onExploreServicesClick = {
                        navController.navigate(CustomerBottomNavItem.Home.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            composable(CustomerBottomNavItem.Profile.route) {
                if (isGuest) {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            text = "Login Required",
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Please login or register to access your profile."
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = onLogout,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Login / Register")
                        }
                    }
                } else {
                    CustomerProfileScreen(
                        authViewModel = authViewModel,
                        onEditProfileClick = onNavigateToEditProfile,
                        onSavedAddressesClick = onNavigateToSavedAddresses,
                        onBookingHistoryClick = {
                            navController.navigate(CustomerBottomNavItem.Bookings.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        onSavedWorkersClick = onNavigateToSavedWorkers,
                        onMyReviewsClick = onNavigateToCustomerReviews,
                        onSettingsClick = onNavigateToSettingsPreferences,
                        onLogout = onLogout
                    )
                }
            }
        }
    }
}