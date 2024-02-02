package com.moronlu18.invoice

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.signup.utils.Locator
import com.moronlu18.InvoiceDavid.entity.InvoiceDao
import com.moronlu18.customer.entity.Customer
import com.moronlu18.customer.entity.CustomerDao
import com.moronlu18.invoice.converter.CustomerEmailTypeConverter
import com.moronlu18.invoice.converter.CustomerIDTypeConverter
import com.moronlu18.invoice.converter.InvoiceIdTypeConverter
import com.moronlu18.invoice.converter.InvoiceInstantLongConverter
import com.moronlu18.invoice.converter.InvoiceStatusConverter
import com.moronlu18.invoice.converter.ItemIdTypeConverter
import com.moronlu18.invoice.converter.ItemTypeConverter
import com.moronlu18.invoice.converter.TaskIdConverter
import com.moronlu18.invoice.converter.TaskStringLongConverter
import com.moronlu18.invoice.entity.Invoice
import com.moronlu18.item.entity.ItemDao
import com.moronlu18.item.entity.item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Database(
    entities = [Invoice::class, /*Task::class*/ item::class, Customer::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    InvoiceIdTypeConverter::class, InvoiceInstantLongConverter::class,
    InvoiceStatusConverter::class, ItemTypeConverter::class, ItemIdTypeConverter::class,CustomerIDTypeConverter::class,TaskIdConverter::class,TaskStringLongConverter::class,CustomerEmailTypeConverter::class
)
abstract class InvoiceDatabase : RoomDatabase() {

    abstract fun invoiceDao(): InvoiceDao

    //abstract fun taskDao(): TaskDao

    abstract fun itemDao(): ItemDao
    abstract  fun CustomerDao():CustomerDao

    companion object {
        @Volatile
        private var INSTANCE: InvoiceDatabase? = null
        fun getInstance(): InvoiceDatabase? {
            return INSTANCE ?: synchronized(InvoiceDatabase::class) {
                val instance = buildDatabase()
                INSTANCE = instance
                instance
            }


        }

        private fun buildDatabase(): InvoiceDatabase {
            return Room.databaseBuilder(
                Locator.requiredApplication,
                InvoiceDatabase::class.java,
                "Invoice"
            ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()//quitarlo
                .addTypeConverter(InvoiceIdTypeConverter())
                .addTypeConverter(InvoiceInstantLongConverter())
                .addTypeConverter(InvoiceStatusConverter())
                .addTypeConverter(TaskStringLongConverter())
                .addTypeConverter(ItemIdTypeConverter())
                .addTypeConverter(ItemTypeConverter())
                .addTypeConverter(CustomerIDTypeConverter())
                .addTypeConverter(TaskIdConverter())
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
            populateUsers()
        }

        private  fun populateUsers() {
            //.let ejecuta el código si no es nulo
           /* instance.let { invoiceDatabase ->
                invoiceDatabase?.userDao()
                    ?.insert(User("Pedro", "pedro@gmail.com", "pedrito", Tipo.User, Perfil.Private))
                invoiceDatabase?.userDao()
                    ?.insert(User("Juan", "juan@gmail.com", "juanito", Tipo.User, Perfil.Private))

            }*/
        }
    }
}