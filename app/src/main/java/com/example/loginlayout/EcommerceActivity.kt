package com.example.loginlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginlayout.ui.theme.LoginLayoutTheme
import androidx.compose.material3.ExperimentalMaterial3Api

class EcommerceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginLayoutTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EcommerceScreen(onBackPressed = { finish() })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcommerceScreen(onBackPressed: () -> Unit) {
    var cartCount by remember { mutableStateOf(0) }
    var showCartMessage by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Barra superior
        TopAppBar(
            title = {
                Text(
                    "Mi Ecommerce",
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackPressed) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
            },
            actions = {
                Box {
                    IconButton(onClick = { showCartMessage = true }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                        if (cartCount > 0) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .offset(x = 10.dp, y = (-10).dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color.Red
                                ) {
                                    Text(
                                        text = cartCount.toString(),
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White
            )
        )

        // Contenido
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    "Productos Destacados",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            items(obtenerProductos()) { product ->
                ProductCard(
                    product = product,
                    onAddToCart = { cartCount++ }
                )
            }
        }
    }

    // Mensaje del carrito
    if (showCartMessage) {
        AlertDialog(
            onDismissRequest = { showCartMessage = false },
            title = { Text("Carrito") },
            text = { Text("Tienes $cartCount productos en el carrito") },
            confirmButton = {
                TextButton(onClick = { showCartMessage = false }) {
                    Text("Cerrar")
                }
            }
        )
    }
}

@Composable
fun ProductCard(product: Product, onAddToCart: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = product.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$${product.price}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Button(
                    onClick = onAddToCart,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Agregar")
                }
            }
        }
    }
}

data class Product(
    val name: String,
    val description: String,
    val price: Double
)

fun obtenerProductos(): List<Product> {
    return listOf(
        Product(
            name = "Portátil ASUS ROG",
            description = "Intel i7-12700H, 16GB DDR5, RTX 3060",
            price = 1299.99
        ),
        Product(
            name = "Samsung Galaxy S23",
            description = "Pantalla 6.1 pulgadas, 256GB, 5G",
            price = 799.99
        ),
        Product(
            name = "Sony WH-1000XM5",
            description = "Cancelación de ruido activa, 30 horas batería",
            price = 349.99
        ),
        Product(
            name = "iPad Air 5",
            description = "10.9 pulgadas, 256GB, compatible Apple Pencil",
            price = 649.99
        ),
        Product(
            name = "Apple Watch Series 8",
            description = "GPS, monitor cardíaco, resistencia agua 50m",
            price = 399.99
        )
    )
}
