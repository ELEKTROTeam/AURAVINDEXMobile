package com.elektro24team.auravindex.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.elektro24team.auravindex.model.AuditLog
import com.elektro24team.auravindex.utils.enums.SettingKey
import com.elektro24team.auravindex.utils.functions.TableCell
import com.elektro24team.auravindex.utils.functions.TableHeaderCell
import com.elektro24team.auravindex.utils.functions.formatUtcToLocalWithHour

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdminAuditLogTable(
    navController: NavController,
    auditLogs: List<AuditLog>
) {
    var rowsPerPage by remember { mutableStateOf(9) }
    var currentPage by remember { mutableStateOf(0) }
    var totalPages = (auditLogs.size + rowsPerPage - 1) / rowsPerPage
    val currentPageAuditLogs = auditLogs.drop(currentPage * rowsPerPage).take(rowsPerPage)
    Text(
        text = "API Audit logs",
        style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(start = 4.dp, top = 16.dp, end = 0.dp, bottom = 16.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
        ) {
            Column {
                Row(modifier = Modifier.padding(vertical = 8.dp)) {
                    TableHeaderCell("User", 180.dp)
                    TableHeaderCell("Action", 180.dp)
                    TableHeaderCell("Object", 180.dp)
                    TableHeaderCell("Date", 160.dp)
                }
                Divider()
                currentPageAuditLogs.forEach { auditLog ->
                    Row(
                        modifier = Modifier
                            .padding(vertical = 6.dp)
                    ) {
                        TableCell(auditLog.user?.email ?: "Unknown", 180.dp)
                        TableCell(auditLog.action.action_code, 180.dp)
                        TableCell(auditLog.affected_object, 180.dp)
                        TableCell(formatUtcToLocalWithHour(auditLog.createdAt), 160.dp)
                    }
                    Divider()
                }
            }
        }
        // Pagination controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { if (currentPage > 0) currentPage-- },
                enabled = currentPage > 0
            ) {
                Text("<")
            }
            Text(
                text = "${currentPage + 1} of $totalPages",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterVertically)
            )
            Button(
                onClick = { if (currentPage < totalPages - 1) currentPage++ },
                enabled = currentPage < totalPages - 1
            ) {
                Text(">")
            }
        }

    }
}
