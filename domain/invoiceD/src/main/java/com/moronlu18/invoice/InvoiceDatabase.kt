package com.moronlu18.invoice

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.signup.utils.Locator
import com.moronlu18.InvoiceDavid.entity.InvoiceDao
import com.moronlu18.InvoiceDavid.entity.InvoiceId
import com.moronlu18.InvoiceDavid.entity.InvoiceStatus
import com.moronlu18.InvoiceDavid.entity.LineaItem
import com.moronlu18.InvoiceDavid.entity.LineaItemDao
import com.moronlu18.customer.entity.Customer
import com.moronlu18.customer.entity.CustomerDao
import com.moronlu18.customer.entity.CustomerId
import com.moronlu18.invoice.converter.CustomerEmailTypeConverter
import com.moronlu18.invoice.converter.CustomerIDTypeConverter
import com.moronlu18.invoice.converter.InvoiceIdTypeConverter
import com.moronlu18.invoice.converter.InvoiceInstantLongConverter
import com.moronlu18.invoice.converter.InvoiceStatusConverter
import com.moronlu18.invoice.converter.ItemIdTypeConverter
import com.moronlu18.invoice.converter.ItemTaxableBoolConverter
import com.moronlu18.invoice.converter.ItemTypeConverter
import com.moronlu18.invoice.entity.Invoice
import com.moronlu18.invoice.ui.firebase.Email
import com.moronlu18.item.entity.ItemDao
import com.moronlu18.item.entity.item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.time.Instant

@Database(
    entities = [Invoice::class, LineaItem::class, item::class,/* Task::class,*/ Customer::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(
    InvoiceIdTypeConverter::class,
    InvoiceInstantLongConverter::class,
    InvoiceStatusConverter::class,
    ItemTypeConverter::class,
    ItemIdTypeConverter::class,
    ItemTaxableBoolConverter::class,
    CustomerIDTypeConverter::class,
    //TaskIdConverter::class,
    //TaskStringLongConverter::class,
    CustomerEmailTypeConverter::class
)
abstract class InvoiceDatabase : RoomDatabase() {

    abstract fun invoiceDao(): InvoiceDao
    abstract fun lineaItemDao(): LineaItemDao
    abstract fun customerDao():CustomerDao

    // abstract fun taskDao(): TaskDao

     abstract fun itemDao(): ItemDao
    //abstract fun CustomerDao(): CustomerDao

    companion object {
        @Volatile
        private var INSTANCE: InvoiceDatabase? = null
        fun getInstance(): InvoiceDatabase {
            return INSTANCE ?: synchronized(InvoiceDatabase::class) {
                val instance = buildDatabase()
                INSTANCE = instance
                instance
            }


        }

        private fun buildDatabase(): InvoiceDatabase {
            return Room.databaseBuilder(
                Locator.requiredApplication, InvoiceDatabase::class.java, "Invoice"
            ).fallbackToDestructiveMigration().allowMainThreadQueries()//quitarlo
                .addTypeConverter(InvoiceIdTypeConverter())
                .addTypeConverter(InvoiceInstantLongConverter())
                .addTypeConverter(InvoiceStatusConverter())
                //.addTypeConverter(TaskStringLongConverter())
                .addTypeConverter(ItemIdTypeConverter())
                .addTypeConverter(ItemTypeConverter())
                .addTypeConverter(ItemTaxableBoolConverter())
                .addTypeConverter(CustomerIDTypeConverter())
                //.addTypeConverter(TaskIdConverter())
                .addTypeConverter(CustomerEmailTypeConverter())
                .addCallback(
                    RoomDbInitializer(INSTANCE)
                ).build()
        }
    }

    class RoomDbInitializer(val instance: InvoiceDatabase?) : RoomDatabase.Callback() {

        private val applicationScope = CoroutineScope(SupervisorJob())

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            applicationScope.launch(Dispatchers.IO) {
                populateDatabase()
            }
        }

        private suspend fun populateDatabase() {
            populateInvoice()
        }

        private fun populateInvoice() {
            //.let ejecuta el código si no es nulo
            getInstance().let { invoiceDatabase ->
                invoiceDatabase.invoiceDao().insert(
                    Invoice(
                        InvoiceId(1),
                        1,
                        SetFecha("2021-01-20"),
                        SetFecha("2021-01-20"),
                        mutableListOf<LineaItem>(
                            LineaItem(
                                1, 1, 1, 1.5, 45.5
                            ), LineaItem(
                                2, 1, 1, 1.5, 45.5
                            )
                        ),
                        InvoiceStatus.Pending
                    )
                )
            }
            getInstance().let { invoiceDatabase ->
                invoiceDatabase.customerDao().insert(
                    Customer(
                        CustomerId(2),"Antonio","Urquiza FAlle",
                        Email("carnetaadspjf@gmail.com"),"6846556414","Málaga","Calle Leonora n46")
                )
            }

            /* instance.let { invoiceDatabase ->
                 invoiceDatabase?.userDao()
                     ?.insert(User("Pedro", "pedro@gmail.com", "pedrito", Tipo.User, Perfil.Private))
                 invoiceDatabase?.userDao()
                     ?.insert(User("Juan", "juan@gmail.com", "juanito", Tipo.User, Perfil.Private))

             }*/
        }

        private fun SetFecha(fecha: String): Instant {
            val dateString = fecha + "T00:00:00Z"
            //val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
            //val localDateTime = LocalDateTime.parse(dateString, formatter)
            val instant = Instant.parse(dateString)
            //return localDateTime.toInstant(ZoneOffset.MAX)
            return instant
        }
    }
}