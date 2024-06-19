package com.example.seniorsprojectui.dbvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.seniorsprojectui.backend.BudgetCategory
import com.example.seniorsprojectui.backend.CurrentUserSession
import com.example.seniorsprojectui.backend.Transaction
import com.example.seniorsprojectui.backend.TransactionDataModel
import com.example.seniorsprojectui.maindb.TestDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class ViewModelTransaction(application: Application) : AndroidViewModel(application) {

    private val db: TestDB = TestDB.getInstance(application)

    var currentUserId : Int = -1
    //    simple lists
    var transactionsList : List<Transaction> = listOf()
    var currentUserTransactions : List<Transaction> = listOf()

    var budget_data : List<BudgetCategory> = listOf()

    var incomeCluster : List<Transaction> = listOf()
    var expenseCluster : List<Transaction> = listOf()

    // live data lists
    private val _transactions = MutableLiveData<List<Transaction>>()
    val transactions: LiveData<List<Transaction>> get() = _transactions

    private val _budgets = MutableLiveData<List<BudgetCategory>>()
    val budgets: LiveData<List<BudgetCategory>> get() = _budgets


    init {
//        fetchTransactions()
//        fetchBudgets()
    }

//     fun fetchTransactions() {
//         viewModelScope.launch(Dispatchers.IO) {
//             transactionsList = db.transactionsDao().getAllTransactions()
//             _transactions.postValue(transactionsList)
//
////            incomeExpenseWiseTransactionCluster()
//             // updating stack holders, every time we fetch data
//             updateAllStakeHolders()
//             // clustering on Basis of Income/Expense
//             makeIncomeExpenseCluster()
//         }
//     }

     fun fetchCurrentUserTransactions(uid : Int)
    {
        viewModelScope.launch(Dispatchers.IO) {
            transactionsList = db.transactionsDao().getCurrentUserTransaction(uid)
            _transactions.postValue(transactionsList)
            // incomeExpenseWiseTransactionCluster()
            // updating stack holders, every time we fetch data
            updateAllStakeHolders()
            makeIncomeExpenseCluster()
        }
    }


    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            db.transactionsDao().insertItem(transaction) // Refresh the list
            fetchCurrentUserTransactions(transaction.uid)
//            fetchTransactions()
        }
    }

    fun deleteAllTransactions(uid : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            db.transactionsDao().deleteAllTransactions(uid)
            fetchCurrentUserTransactions(CurrentUserSession.currentUserId) // Refresh the list
//            fetchTransactions()
        }
    }

    fun deleteTransaction(transaction : Transaction){
        viewModelScope.launch(Dispatchers.IO) {
            db.transactionsDao().deleteItem(transaction)
            fetchCurrentUserTransactions(transaction.uid) // Refresh the lis
//            fetchTransactions()
        }
    }

    fun deleteById(id : Int)
    {
        viewModelScope.launch {
            db.transactionsDao().deleteById(id)
//            fetchTransactions()
            fetchCurrentUserTransactions(CurrentUserSession.currentUserId) // Refresh the list
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

    fun getTransactionItemById(tid: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionsList.find { it.Tid == tid }?.let { deleteTransaction(it) }
        }
    }

    fun insertBudget(item : BudgetCategory){
        viewModelScope.launch (Dispatchers.IO){
            db.budgetCategoryDao().insertBudget(item)
            fetchBudgetsByUserId(item.uid)
        }
    }


    fun updateBudget(item: BudgetCategory)
    {
        viewModelScope.launch(Dispatchers.IO) {
            db.budgetCategoryDao().updateBudget(item)
            fetchBudgetsByUserId(item.uid)
        }
    }


//    private fun fetchBudgets(){
//        viewModelScope.launch (Dispatchers.IO){
//             budget_data = db.budgetCategoryDao().getAllBudgets()
//            _budgets.postValue(budget_data)
//        }
//    }
//
     fun fetchBudgetsByUserId(uid : Int){
        viewModelScope.launch (Dispatchers.IO){
             budget_data = db.budgetCategoryDao().getUserBudgets(uid)
            _budgets.postValue(budget_data)
        }
    }

    // clustering transactions
    // by date and income/expense

    fun dateWiseTransactionsCluster() {

        viewModelScope.launch(Dispatchers.IO) {
            val dateFilteredMap = mutableMapOf<String, MutableList<Transaction>>()
            for (entry in transactionsList) {
                if (dateFilteredMap.containsKey(entry.date)) {
                    dateFilteredMap[entry.date]?.add(entry)
                } else {
                    dateFilteredMap[entry.date] = mutableListOf(entry)
                }
            }
        }
    }

    private fun makeIncomeExpenseCluster(){
        viewModelScope.launch(Dispatchers.IO) {
            incomeCluster = transactionsList.filter { it.transactionType == "income" }
            expenseCluster = transactionsList.filter { it.transactionType == "expense" }
        }
    }


}
