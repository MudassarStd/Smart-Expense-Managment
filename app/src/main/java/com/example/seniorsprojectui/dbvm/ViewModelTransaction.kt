package com.example.seniorsprojectui.dbvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.seniorsprojectui.backend.BudgetCategory
import com.example.seniorsprojectui.backend.Transaction
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.maindb.MainTransactionsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class ViewModelTransaction(application: Application) : AndroidViewModel(application) {

    private val db: MainTransactionsDatabase = Room.databaseBuilder(
        application,
        MainTransactionsDatabase::class.java, "Main_Transaction_db"
    ).build()


//    simple list
    var transactionsList : List<Transaction> = listOf()
    // live data lists
    private val _transactions = MutableLiveData<List<Transaction>>()
    val transactions: LiveData<List<Transaction>> get() = _transactions

    private val _budgets = MutableLiveData<List<BudgetCategory>>()
    val budgets: LiveData<List<BudgetCategory>> get() = _budgets


    init {
        fetchTransactions()
//        fetchBudgets()
    }

    private fun fetchTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
             transactionsList = db.transacactionDaoConnector().getAllTransactions()
            _transactions.postValue(transactionsList)

            // updating stack holders, every time we fetch data
            updateAllStakeHolders()
        }
    }

    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            db.transacactionDaoConnector().insertItem(transaction)
            fetchTransactions() // Refresh the list

        }
    }

    fun deleteAllTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            db.transacactionDaoConnector().deleteAllTransactions()
            fetchTransactions() // Refresh the list
        }
    }

    // update transaction stackholders
    fun updateAllStakeHolders() {
        viewModelScope.launch ( Dispatchers.IO){

            TransactionDataModel.updateHomeFragIncomeExpenseStatus(transactionsList)
            TransactionDataModel.getCategoryWiseAmountSpent(transactionsList)
            TransactionDataModel.updateDataForFinancialReport(transactionsList)
        }
    }

//    fun insertBudget(item : BudgetCategory){
//        viewModelScope.launch (Dispatchers.IO){
//            db.transacactionDaoConnector().insertBudget(item)
////            fetchBudgets()
//
//        }
//    }
//    fun fetchBudgets(){
//        viewModelScope.launch (Dispatchers.IO){
//            val budgets = db.transacactionDaoConnector().getAllBudgets()
//            _budgets.postValue(budgets)
//        }
//    }
}
