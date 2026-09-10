package com.example.cooperativegig.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cooperativegig.presentation.admin.AdminDashboardScreen
import com.example.cooperativegig.presentation.auth.AdminLoginScreen
import com.example.cooperativegig.presentation.auth.AuthViewModel
import com.example.cooperativegig.presentation.auth.AuthViewModelFactory
import com.example.cooperativegig.presentation.auth.LoginScreen
import com.example.cooperativegig.presentation.auth.OnboardingScreen
import com.example.cooperativegig.presentation.auth.RegisterScreen
import com.example.cooperativegig.presentation.auth.SplashScreen
import com.example.cooperativegig.presentation.customer.CustomerMainScreen
import com.example.cooperativegig.presentation.customer.booking.BookingTrackingScreen
import com.example.cooperativegig.presentation.customer.booking.CreateBookingScreen
import com.example.cooperativegig.presentation.customer.booking.EmergencyServiceScreen
import com.example.cooperativegig.presentation.customer.location.NearbyWorkersMapScreen
import com.example.cooperativegig.presentation.customer.location.SelectLocationScreen
import com.example.cooperativegig.presentation.customer.notification.NotificationCenterScreen
import com.example.cooperativegig.presentation.customer.payment.PaymentScreen
import com.example.cooperativegig.presentation.customer.profile.CustomerReviewsScreen
import com.example.cooperativegig.presentation.customer.profile.EditProfileScreen
import com.example.cooperativegig.presentation.customer.profile.SavedAddressesScreen
import com.example.cooperativegig.presentation.customer.profile.SavedWorkersScreen
import com.example.cooperativegig.presentation.customer.profile.SettingsPreferencesScreen
import com.example.cooperativegig.presentation.customer.rating.RatingScreen
import com.example.cooperativegig.presentation.customer.service.ServiceDetailScreen
import com.example.cooperativegig.presentation.customer.worker.WorkerDetailScreen
import com.example.cooperativegig.presentation.worker.WorkerMainScreen
import com.example.cooperativegig.presentation.worker.verification.WorkerVerificationScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val authViewModel: AuthViewModel =
        viewModel(factory = AuthViewModelFactory())

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        // ---------------------------------------------------------
        // SPLASH
        // ---------------------------------------------------------
        composable("splash") {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate("onboarding") {
                        popUpTo("splash") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // ---------------------------------------------------------
        // ONBOARDING
        // ---------------------------------------------------------
        composable("onboarding") {
            OnboardingScreen(
                onLogin = {
                    navController.navigate("login")
                },
                onSkip = {
                    navController.navigate("guest_customer_home") {
                        popUpTo("onboarding") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // ---------------------------------------------------------
        // LOGIN
        // ---------------------------------------------------------
        composable("login") {
            LoginScreen(
                viewModel = authViewModel,

                onNavigateToRegister = {
                    navController.navigate("register")
                },

                onNavigateToCustomerHome = {
                    navController.navigate("customer_home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },

                onNavigateToWorkerHome = {
                    navController.navigate("worker_home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // ---------------------------------------------------------
        // REGISTER
        // ---------------------------------------------------------
        composable("register") {
            RegisterScreen(
                viewModel = authViewModel,

                onNavigateToLogin = {
                    navController.popBackStack()
                },

                onNavigateToCustomerHome = {
                    navController.navigate("customer_home") {
                        popUpTo("register") {
                            inclusive = true
                        }
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },

                onNavigateToWorkerHome = {
                    navController.navigate("worker_home") {
                        popUpTo("register") {
                            inclusive = true
                        }
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // ---------------------------------------------------------
        // ADMIN LOGIN
        // ---------------------------------------------------------
        composable("admin_login") {
            AdminLoginScreen(
                viewModel = authViewModel,

                onLoginSuccess = {
                    navController.navigate("admin_dashboard") {
                        popUpTo("admin_login") {
                            inclusive = true
                        }
                    }
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------------------------------------------------
        // NORMAL CUSTOMER HOME
        // ---------------------------------------------------------
        composable("customer_home") {
            CustomerMainScreen(
                authViewModel = authViewModel,
                isGuest = false,

                onNavigateToServiceDetail = { serviceId ->
                    navController.navigate("service_detail/$serviceId")
                },

                onNavigateToWorkerDetail = { workerId ->
                    navController.navigate("worker_detail/$workerId")
                },

                onNavigateToEmergencyBooking = {
                    navController.navigate("emergency_booking")
                },

                onNavigateToSelectLocation = {
                    navController.navigate("select_location")
                },

                onNavigateToNearbyMap = {
                    navController.navigate("nearby_workers_map")
                },

                onNavigateToNotifications = {
                    navController.navigate("notifications")
                },

                onNavigateToEditProfile = {
                    navController.navigate("edit_profile")
                },

                onNavigateToSavedAddresses = {
                    navController.navigate("saved_addresses")
                },

                onNavigateToSavedWorkers = {
                    navController.navigate("saved_workers")
                },

                onNavigateToCustomerReviews = {
                    navController.navigate("customer_reviews")
                },

                onNavigateToSettingsPreferences = {
                    navController.navigate("settings_preferences")
                },

                onLogout = {
                    navController.navigate("login") {
                        popUpTo("customer_home") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // ---------------------------------------------------------
        // GUEST CUSTOMER HOME
        // ---------------------------------------------------------
        composable("guest_customer_home") {
            CustomerMainScreen(
                authViewModel = authViewModel,
                isGuest = true,

                onNavigateToServiceDetail = { serviceId ->
                    navController.navigate("service_detail/$serviceId?guest=true")
                },

                onNavigateToWorkerDetail = { workerId ->
                    navController.navigate("worker_detail/$workerId")
                },

                onNavigateToEmergencyBooking = {
                    navController.navigate("emergency_booking")
                },

                onNavigateToSelectLocation = {
                    navController.navigate("select_location")
                },

                onNavigateToNearbyMap = {
                    navController.navigate("nearby_workers_map")
                },

                onNavigateToNotifications = {
                    navController.navigate("notifications")
                },

                onNavigateToEditProfile = {
                    navController.navigate("edit_profile")
                },

                onNavigateToSavedAddresses = {
                    navController.navigate("saved_addresses")
                },

                onNavigateToSavedWorkers = {
                    navController.navigate("saved_workers")
                },

                onNavigateToCustomerReviews = {
                    navController.navigate("customer_reviews")
                },

                onNavigateToSettingsPreferences = {
                    navController.navigate("settings_preferences")
                },

                onLogout = {
                    navController.navigate("onboarding") {
                        popUpTo("guest_customer_home") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // ---------------------------------------------------------
        // SELECT LOCATION
        // ---------------------------------------------------------
        composable("select_location") {
            SelectLocationScreen(
                onLocationConfirmed = { address, lat, lng ->
                    navController.popBackStack()
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------------------------------------------------
        // NEARBY WORKERS MAP
        // ---------------------------------------------------------
        composable("nearby_workers_map") {
            NearbyWorkersMapScreen(
                onWorkerSelect = { workerId ->
                    navController.navigate("worker_detail/$workerId")
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------------------------------------------------
        // EDIT PROFILE
        // ---------------------------------------------------------
        composable("edit_profile") {
            EditProfileScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------------------------------------------------
        // SAVED ADDRESSES
        // ---------------------------------------------------------
        composable("saved_addresses") {
            SavedAddressesScreen(
                onSelectOnMapClick = {
                    navController.navigate("select_location")
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------------------------------------------------
        // SAVED WORKERS
        // ---------------------------------------------------------
        composable("saved_workers") {
            SavedWorkersScreen(
                onWorkerClick = { workerId ->
                    navController.navigate("worker_detail/$workerId")
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------------------------------------------------
        // CUSTOMER REVIEWS
        // ---------------------------------------------------------
        composable("customer_reviews") {
            CustomerReviewsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------------------------------------------------
        // SETTINGS
        // ---------------------------------------------------------
        composable("settings_preferences") {
            SettingsPreferencesScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------------------------------------------------
        // SERVICE DETAIL
        // ---------------------------------------------------------
        // ---------------------------------------------------------
// SERVICE DETAIL
// ---------------------------------------------------------
        composable(
            route = "service_detail/{serviceId}?guest={guest}",
            arguments = listOf(
                navArgument("serviceId") {
                    type = NavType.LongType
                },
                navArgument("guest") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { backStackEntry ->

            val serviceId =
                backStackEntry.arguments?.getLong("serviceId") ?: 0L

            val isGuest =
                backStackEntry.arguments?.getBoolean("guest") ?: false

            ServiceDetailScreen(
                serviceId = serviceId,

                onBookNow = { id ->
                    if (isGuest) {
                        navController.navigate("login_required")
                    } else {
                        navController.navigate("create_booking/$id")
                    }
                },

                onWorkerClick = { workerId ->
                    navController.navigate("worker_detail/$workerId")
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }
        // ---------------------------------------------------------
// LOGIN REQUIRED
// ---------------------------------------------------------
        composable("login_required") {
            androidx.compose.material3.AlertDialog(
                onDismissRequest = {
                    navController.popBackStack()
                },

                title = {
                    androidx.compose.material3.Text("Login Required")
                },

                text = {
                    androidx.compose.material3.Text(
                        "Please login or register to book a service."
                    )
                },

                confirmButton = {
                    androidx.compose.material3.TextButton(
                        onClick = {
                            navController.navigate("login") {
                                popUpTo("guest_customer_home") {
                                    inclusive = false
                                }
                            }
                        }
                    ) {
                        androidx.compose.material3.Text("Login")
                    }
                },

                dismissButton = {
                    androidx.compose.material3.TextButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        androidx.compose.material3.Text("Cancel")
                    }
                }
            )
        }

        // ---------------------------------------------------------
        // WORKER DETAIL
        // ---------------------------------------------------------
        composable(
            route = "worker_detail/{workerId}",
            arguments = listOf(
                navArgument("workerId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val workerId =
                backStackEntry.arguments?.getString("workerId") ?: "w1"

            WorkerDetailScreen(
                workerId = workerId,

                onBookWorker = {
                    navController.navigate("create_booking/1")
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------------------------------------------------
        // CREATE BOOKING
        // ---------------------------------------------------------
        composable(
            route = "create_booking/{serviceId}",
            arguments = listOf(
                navArgument("serviceId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->

            val serviceId =
                backStackEntry.arguments?.getLong("serviceId") ?: 0L

            CreateBookingScreen(
                serviceId = serviceId,
                isEmergency = false,

                onBookingCreated = { bookingId ->
                    navController.navigate("booking_tracking/$bookingId") {
                        popUpTo("customer_home") {
                            inclusive = false
                        }
                    }
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------------------------------------------------
        // EMERGENCY BOOKING
        // ---------------------------------------------------------
        composable("emergency_booking") {
            EmergencyServiceScreen(
                onDispatchRequested = { bookingId ->
                    navController.navigate("booking_tracking/$bookingId") {
                        popUpTo("customer_home") {
                            inclusive = false
                        }
                    }
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------------------------------------------------
        // BOOKING TRACKING
        // ---------------------------------------------------------
        composable(
            route = "booking_tracking/{bookingId}",
            arguments = listOf(
                navArgument("bookingId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->

            val bookingId =
                backStackEntry.arguments?.getLong("bookingId") ?: 0L

            BookingTrackingScreen(
                bookingId = bookingId,

                onPaymentClick = {
                    navController.navigate("payment/$bookingId")
                },

                onBackHome = {
                    navController.navigate("customer_home") {
                        popUpTo("customer_home") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // ---------------------------------------------------------
        // PAYMENT
        // ---------------------------------------------------------
        composable(
            route = "payment/{bookingId}",
            arguments = listOf(
                navArgument("bookingId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->

            val bookingId =
                backStackEntry.arguments?.getLong("bookingId") ?: 0L

            PaymentScreen(
                bookingId = bookingId,

                onPaymentSuccess = {
                    navController.navigate("rating/$bookingId") {
                        popUpTo("booking_tracking/$bookingId") {
                            inclusive = true
                        }
                    }
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------------------------------------------------
        // RATING
        // ---------------------------------------------------------
        composable(
            route = "rating/{bookingId}",
            arguments = listOf(
                navArgument("bookingId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->

            val bookingId =
                backStackEntry.arguments?.getLong("bookingId") ?: 0L

            RatingScreen(
                bookingId = bookingId,

                onSubmitted = {
                    navController.navigate("customer_home") {
                        popUpTo("customer_home") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // ---------------------------------------------------------
        // NOTIFICATIONS
        // ---------------------------------------------------------
        composable("notifications") {
            NotificationCenterScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------------------------------------------------
        // WORKER HOME
        // ---------------------------------------------------------
        composable("worker_home") {
            WorkerMainScreen(
                authViewModel = authViewModel,

                onNavigateToVerification = {
                    navController.navigate("worker_verification")
                },

                onNavigateToAdminLogin = {
                    navController.navigate("admin_login")
                },

                onLogout = {
                    navController.navigate("login") {
                        popUpTo("worker_home") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // ---------------------------------------------------------
        // WORKER VERIFICATION
        // ---------------------------------------------------------
        composable("worker_verification") {
            WorkerVerificationScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------------------------------------------------
        // ADMIN DASHBOARD
        // ---------------------------------------------------------
        composable("admin_dashboard") {
            AdminDashboardScreen(
                onLogout = {
                    authViewModel.logout()

                    navController.navigate("login") {
                        popUpTo("admin_dashboard") {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}