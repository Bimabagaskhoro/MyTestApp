package com.bimabk.detail.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bimabk.common.helper.UiState
import com.bimabk.data.uimodel.UserDetailUiModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    onBack: () -> Unit = {},
    detailUserViewModel: DetailUserViewModel = koinViewModel(),
) {
    val uiState by detailUserViewModel.userDetail.collectAsStateWithLifecycle()

    DetailSection(
        uiState = uiState,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSection(
    uiState: UiState<UserDetailUiModel>,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "detail",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState) {
                is UiState.Error -> {}
                is UiState.Init -> {}
                is UiState.Loading -> {}
                is UiState.Success -> {
                    DetailSuccessState(
                        data = uiState.data
                    )
                }
            }
        }
    }
}

@Composable
fun DetailSuccessState(
    data: UserDetailUiModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Personal Information Section
        DetailSection(
            title = "Personal Information",
            icon = Icons.Default.Person
        ) {
            DetailItem(
                label = "Full Name",
                value = data.name,
                icon = Icons.Default.Person
            )
            DetailItem(
                label = "Username",
                value = data.username,
                icon = Icons.Default.Person
            )
            DetailItem(
                label = "User ID",
                value = data.userId.toString(),
                icon = Icons.Default.Person
            )
        }

        // Contact Information Section
        DetailSection(
            title = "Contact Information",
            icon = Icons.Default.Phone
        ) {
            DetailItem(
                label = "Email",
                value = data.email,
                icon = Icons.Default.Email
            )
            DetailItem(
                label = "Phone",
                value = data.phone,
                icon = Icons.Default.Phone
            )
            DetailItem(
                label = "Website",
                value = data.website,
                icon = Icons.Default.Info
            )
        }

        // Address Information Section
        DetailSection(
            title = "Address Information",
            icon = Icons.Default.LocationOn
        ) {
            DetailItem(
                label = "Street",
                value = data.addressStreet,
                icon = Icons.Default.LocationOn
            )
            DetailItem(
                label = "Suite",
                value = data.addressSuite,
                icon = Icons.Default.LocationOn
            )
            DetailItem(
                label = "City",
                value = data.addressCity,
                icon = Icons.Default.LocationOn
            )
            DetailItem(
                label = "Zipcode",
                value = data.addressZipcode,
                icon = Icons.Default.LocationOn
            )
            DetailItem(
                label = "Coordinates",
                value = "Lat: ${data.addressGeoLat}, Lng: ${data.addressGeoLng}",
                icon = Icons.Default.LocationOn
            )
        }

        // Company Information Section
        DetailSection(
            title = "Company Information",
            icon = Icons.Default.AccountCircle
        ) {
            DetailItem(
                label = "Company Name",
                value = data.companyName,
                icon = Icons.Default.Home
            )
            DetailItem(
                label = "Catch Phrase",
                value = data.companyCatchPhrase,
                icon = Icons.Default.Home
            )
            DetailItem(
                label = "Business",
                value = data.companyBs,
                icon = Icons.Default.Home
            )
        }
    }
}

@Composable
fun DetailSection(
    title: String,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            content()
        }
    }
}

@Composable
fun DetailItem(
    label: String,
    value: String,
    icon: ImageVector
) {
    if (value.isNotEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview
@Composable
fun PreviewDetailSection() {
    MaterialTheme {
        DetailSuccessState(
            data = UserDetailUiModel(
                website = "webdotkom.com",
                phone = "08123456789",
                companyBs = "dummy",
                companyCatchPhrase = "dummy",
                companyName = "dummy",
                addressZipcode = "dummy",
                addressGeoLng = "dummy",
                addressGeoLat = "dummy",
                addressSuite = "dummy",
                addressCity = "dummy",
                addressStreet = "dummy",
                userId = 123,
                name = "jon doe",
                email = "jondoe@example.com",
                username = "@jhondoe"
            )
        )
    }
}