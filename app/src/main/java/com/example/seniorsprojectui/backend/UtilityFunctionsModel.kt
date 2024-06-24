package com.example.seniorsprojectui.backend

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter


// steps to read/open a file
// create file provider instance in application<> (manifest.xml)
// create file_paths.xml in res/xml
// define permissions for read/write in device media

class UtilityFunctionsModel {
    companion object{

        fun copyFileToExternalStorage(context: Context) {
            val sourceFile = File(context.filesDir, "transactions.csv")
            val externalDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val destinationFile = File(externalDir, "transactions.csv")

            try {
                FileInputStream(sourceFile).use { input ->
                    FileOutputStream(destinationFile).use { output ->
                        val buffer = ByteArray(1024)
                        var length: Int
                        while (input.read(buffer).also { length = it } > 0) {
                            output.write(buffer, 0, length)
                        }
                    }
                }
                Toast.makeText(context,"File copied to: ${destinationFile.absolutePath}", Toast.LENGTH_SHORT).show()
                Log.d("saveTransactionsToCSV", "Saving CSV to ${destinationFile.absolutePath}")

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        fun saveTransactionsToCSV(transactions: List<Transaction>, context: Context) {
            val csvHeader = "Tid,Time,Date,Month,Amount,Category,Wallet,Description,Attachment,TransactionType \n"
            val csvData = StringBuilder(csvHeader)

            for (transaction in transactions) {
                csvData.append("${transaction.Tid},${transaction.time},${transaction.date},${transaction.month},${transaction.amount},${transaction.category},${transaction.wallet},${transaction.description},${transaction.attachment},${transaction.transactionType}\n")
            }
            Log.d("saveTransactionsToCSV", "Data List: ${transactions}")

            try {
                val fileName = "transactions.csv"
                val file = File(context.filesDir, fileName)
                Log.d("saveTransactionsToCSV", "Saving CSV to ${file.absolutePath}")

                // Check if the file already exists and delete it if so
                if (file.exists()) {
                    file.delete()
//                    Log.d("saveTransactionsToCSV", "Existing file deleted")
                }

                val fileOutputStream = FileOutputStream(file)
                val outputStreamWriter = OutputStreamWriter(fileOutputStream)
                outputStreamWriter.write(csvData.toString())
                outputStreamWriter.close()
                fileOutputStream.close()

                copyFileToExternalStorage(context)

                Toast.makeText(context, "CSV file saved to ${file.absolutePath}", Toast.LENGTH_LONG).show()
                Log.d("saveTransactionsToCSV", "CSV file successfully saved")

                // opening file
//                openCSVFile(file, context)

            } catch (e: Exception) {

                Log.e("saveTransactionsToCSV", "Error saving CSV file: ${e.message}")
                Toast.makeText(context, "Failed to save CSV file", Toast.LENGTH_SHORT).show()
            }
        }

        // Opening CSV file

//        private fun openCSVFile(file: File, context: Context) {
//
//            // authority defines: who's gonna read or access file
//            val fileUri: Uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
//
//            val intent = Intent(Intent.ACTION_VIEW).apply {
//                setDataAndType(fileUri, "text/csv")
//                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            }
//
//            if (intent.resolveActivity(context.packageManager) != null) {
//                context.startActivity(intent)
//            } else {
//                Toast.makeText(context, "No application found to open CSV file", Toast.LENGTH_SHORT).show()
//            }
//        }


        fun saveTransactionsToPDF(transactions: List<Transaction>, context: Context) {
            try {
                val fileDir = context.getDir("documents", Context.MODE_PRIVATE)
                val file = File(fileDir, "transactions.pdf")

                val pdfDocument = PdfDocument()
                val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
                val page = pdfDocument.startPage(pageInfo)

                val canvas = page.canvas
                val paint = Paint()
                paint.textSize = 12f

                var yPosition = 40f

                // Header
                canvas.drawText("Tid,Time,Date,Month,Amount,Category,Wallet,Description,Attachment,TransactionType", 10f, yPosition, paint)
                yPosition += paint.descent() - paint.ascent()

                // Transactions
                for (transaction in transactions) {
                    val text = "${transaction.Tid},${transaction.time},${transaction.date},${transaction.month},${transaction.amount},${transaction.category},${transaction.wallet},${transaction.description},${transaction.attachment},${transaction.transactionType}"
                    yPosition += paint.descent() - paint.ascent()
                    canvas.drawText(text, 10f, yPosition, paint)
                }

                pdfDocument.finishPage(page)

                val fileOutputStream = FileOutputStream(file)
                pdfDocument.writeTo(fileOutputStream)
                pdfDocument.close()
                fileOutputStream.close()

                Toast.makeText(context, "PDF file saved to ${file.absolutePath}", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {

                Log.e("PDF Not Saved", "${e.message}")
                Toast.makeText(context, "Failed to save PDF file", Toast.LENGTH_SHORT).show()
            }
        }


        private fun openPDFFile(file: File, context: Context) {
            val fileUri: Uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(fileUri, "application/pdf")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "No application found to open PDF file", Toast.LENGTH_SHORT).show()
            }
        }


        // filteration plant

//        fun generateDummyTransactions(): List<Transaction> {
//            return listOf(
//                Transaction(
//                    Tid = 1,
//                    time = "10:00 AM",
//                    date = "2024-05-01",
//                    month = "May",
//                    amount = "100.00",
//                    category = "Groceries",
//                    wallet = "Credit Card",
//                    description = "Bought groceries",
//                    attachment = "receipt1.jpg",
//                    transactionType = "Expense",
//                    uid = 101
//                ),
//                Transaction(
//                    Tid = 2,
//                    time = "12:30 PM",
//                    date = "2024-05-02",
//                    month = "May",
//                    amount = "50.00",
//                    category = "Transport",
//                    wallet = "Debit Card",
//                    description = "Taxi fare",
//                    attachment = "receipt2.jpg",
//                    transactionType = "Expense",
//                    uid = 102
//                ),
//                Transaction(
//                    Tid = 3,
//                    time = "03:00 PM",
//                    date = "2024-05-03",
//                    month = "May",
//                    amount = "200.00",
//                    category = "Salary",
//                    wallet = "Bank Account",
//                    description = "Monthly salary",
//                    attachment = "payslip.jpg",
//                    transactionType = "Income",
//                    uid = 103
//                )
//                // Add more transactions as needed
//            )
//        }


    }
}