package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
          Scaffold(
            topBar = {
              Column {
                TopAppBar(
                  title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                      Box(
                        modifier = Modifier
                          .size(40.dp)
                          .clip(RoundedCornerShape(20.dp))
                          .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                      ) {
                        Text("م", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                      }
                      Spacer(modifier = Modifier.width(12.dp))
                      Column {
                        Text("منصة", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)
                        Text("ألعاب المجد", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                      }
                    }
                  },
                  actions = {
                    Row(
                      verticalAlignment = Alignment.CenterVertically,
                      modifier = Modifier
                        .padding(end = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                      Text("1,250", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                      Spacer(modifier = Modifier.width(8.dp))
                      Box(modifier = Modifier.size(16.dp).background(MaterialTheme.colorScheme.secondary))
                    }
                  },
                  colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                  )
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outline, thickness = 1.dp)
              }
            },
            bottomBar = { GameBottomNavigation() },
            floatingActionButton = {
              ExtendedFloatingActionButton(
                onClick = { /* Handle Gacha */ },
                icon = { Icon(Icons.Default.Star, contentDescription = "استدعاء") },
                text = { Text("استدعاء قادة") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
              )
            },
            modifier = Modifier.fillMaxSize()
          ) { innerPadding ->
            GameDashboard(modifier = Modifier.padding(innerPadding))
          }
        }
      }
    }
  }
}

@Composable
fun GameBottomNavigation() {
  var selectedItem by remember { mutableIntStateOf(0) }
  val items = listOf(
    Triple("المدينة", Icons.Default.Home, 0),
    Triple("الجيش", Icons.Default.Shield, 1),
    Triple("التحالفات", Icons.Default.Group, 2),
    Triple("شجرة التطور", Icons.Default.AccountTree, 3)
  )

  Column {
    HorizontalDivider(color = MaterialTheme.colorScheme.outline, thickness = 1.dp)
    NavigationBar(
      containerColor = MaterialTheme.colorScheme.surface,
      modifier = Modifier.padding(bottom = 8.dp)
    ) {
      items.forEach { (title, icon, index) ->
        NavigationBarItem(
          icon = { Icon(icon, contentDescription = title) },
          label = { Text(title, fontSize = 10.sp, fontWeight = if (selectedItem == index) FontWeight.Bold else FontWeight.Medium) },
          selected = selectedItem == index,
          onClick = { selectedItem = index },
          colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            selectedIconColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
          )
        )
      }
    }
  }
}

@Composable
fun GameDashboard(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    ResourceBar()
    CityView()
    ActionCards()
  }
}

@Composable
fun ResourceBar() {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    ResourceItem(title = "الغذاء", amount = "5,400", icon = Icons.Default.Eco, modifier = Modifier.weight(1f))
    ResourceItem(title = "الذهب", amount = "12,000", icon = Icons.Default.MonetizationOn, modifier = Modifier.weight(1f))
    ResourceItem(title = "العلوم", amount = "3,200", icon = Icons.Default.Science, modifier = Modifier.weight(1f))
  }
}

@Composable
fun ResourceItem(title: String, amount: String, icon: ImageVector, modifier: Modifier = Modifier) {
  Card(
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    shape = RoundedCornerShape(16.dp),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    modifier = modifier
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp).fillMaxWidth()
    ) {
      Icon(
        imageVector = icon,
        contentDescription = title,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(16.dp)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Column {
        Text(text = title, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = amount, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
      }
    }
  }
}

@Composable
fun CityView() {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(220.dp)
      .clip(RoundedCornerShape(24.dp))
      .background(MaterialTheme.colorScheme.surfaceVariant)
      .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(24.dp)),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Icon(
        Icons.Default.Castle,
        contentDescription = "قصر الخلافة",
        modifier = Modifier.size(64.dp),
        tint = MaterialTheme.colorScheme.primary
      )
      Spacer(modifier = Modifier.height(16.dp))
      Text("قصر الخلافة - المستوى 5", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
      Text("اضغط للتطوير", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
  }
}

@Composable
fun ActionCards() {
  Column {
    Row(
      modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp, start = 4.dp, end = 4.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text("الإجراءات", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
      Text("عرض الكل", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.primary)
    }
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      ActionCard(title = "تجنيد الجنود", subtitle = "RPG • قتال", icon = Icons.Default.PersonAdd, modifier = Modifier.weight(1f))
      ActionCard(title = "بحث علمي", subtitle = "Casual • ذكاء", icon = Icons.Default.MenuBook, modifier = Modifier.weight(1f))
    }
  }
}

@Composable
fun ActionCard(title: String, subtitle: String, icon: ImageVector, modifier: Modifier = Modifier) {
  Card(
    modifier = modifier,
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    shape = RoundedCornerShape(16.dp),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
  ) {
    Column(
      modifier = Modifier.fillMaxWidth().padding(12.dp)
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(16f / 9f)
          .clip(RoundedCornerShape(12.dp))
          .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
      ) {
        Box(
          modifier = Modifier.size(32.dp).border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(16.dp)
          )
        }
      }
      Spacer(modifier = Modifier.height(8.dp))
      Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, maxLines = 1)
      Text(subtitle, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
  }
}
