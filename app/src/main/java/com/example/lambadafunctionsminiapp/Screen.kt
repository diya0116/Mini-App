package com.example.lambadafunctionsminiapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lambadafunctionsminiapp.ui.theme.Blackish
import com.example.lambadafunctionsminiapp.ui.theme.Gray1
import com.example.lambadafunctionsminiapp.ui.theme.Gray2
import com.example.lambadafunctionsminiapp.ui.theme.Green
import com.example.lambadafunctionsminiapp.ui.theme.Orange
import com.example.lambadafunctionsminiapp.ui.theme.White
import com.example.lambadafunctionsminiapp.ui.theme.Yellow

@Composable
fun Screen() {

    var nameOfStudentInScreen by remember { mutableStateOf("") }
    var rollNoInScreen by remember { mutableStateOf("") }
    var yearOfPassingInScreen by remember { mutableStateOf("") }
    var showUserDetailsDialog by remember { mutableStateOf(false) }

    var showSubjectEntryDialog by remember { mutableStateOf(false) }
    var subjectListInScreen by remember { mutableStateOf(listOf<String>()) }

    var subjectMarksList by remember { mutableStateOf<List<String>>(emptyList()) }

    // a list of marks corresponding to each subject (initialized with empty strings)


    Column( // full screen in this column
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 32.dp, horizontal = 16.dp)

    ) {

        if(nameOfStudentInScreen.isNotEmpty() && rollNoInScreen.isNotEmpty() && yearOfPassingInScreen.isNotEmpty()) {

            Card( // for displaying student information
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Green)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Welcome $nameOfStudentInScreen!", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Blackish)
                    Text(text = "Roll No. $rollNoInScreen", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Blackish)
                    Text(text = "Year of passing: $yearOfPassingInScreen", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Blackish)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (subjectListInScreen.isNotEmpty()) {

                Card(
                    colors = CardDefaults.cardColors(containerColor = Gray1)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(all = 16.dp),
                    ) {
                        Text(
                            text = "Subjects and Performance:",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Blackish
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        subjectListInScreen.forEachIndexed { index, subject ->
                            var showMarksDialog by remember { mutableStateOf(false) }

                            Row(
                                modifier = Modifier.fillMaxWidth()
                                   .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){

                                Text(
                                    text = subject,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Blackish
                                )

                                if(subjectMarksList[index].isEmpty()) {
                                    TextButton(
                                        onClick = {
                                            showMarksDialog = true
                                        }
                                    ) {
                                        Text(
                                            text = "Enter Marks",
                                            fontSize = 16.sp,
                                            fontStyle = FontStyle.Italic,
                                            color = Orange
                                        )
                                    }
                                } else {
                                    val marks = subjectMarksList[index].toInt()

                                    Column(
                                        modifier = Modifier.fillMaxWidth()
                                            .padding(horizontal = 16.dp),
                                        horizontalAlignment = Alignment.Start
                                    ){
                                        Text(
                                            text = "Marks: $marks",
                                            color = Blackish
                                        )
                                        Text(
                                            text = "Performance: ${getPerformance(marks)}",
                                            color = getPerformanceColor(marks)
                                        )
                                    }
                                }

                                if(showMarksDialog) {

                                    var marksEntered by remember { mutableStateOf("") }

                                    AlertDialog(
                                        title = {
                                            Text(text = "Enter your marks", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Blackish)
                                        },
                                        text = {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                            ) {
                                                TextField(
                                                    label = { Text(text = "Marks (0 - 100)", fontSize = 16.sp)},
                                                    value = marksEntered ,
                                                    onValueChange = {marksEntered = it},
                                                    modifier = Modifier.fillMaxWidth(),
                                                    colors = TextFieldDefaults.colors(
                                                        focusedContainerColor = Gray1,
                                                        unfocusedContainerColor = Gray1,
                                                        focusedIndicatorColor = Green,
                                                        unfocusedIndicatorColor = Gray2,
                                                        focusedTextColor = Blackish,
                                                        unfocusedTextColor = Blackish
                                                    )
                                                )
                                            }

                                        },
                                        onDismissRequest = {
                                            showMarksDialog = false
                                        },
                                        confirmButton = {
                                            Button(
                                                onClick = {
                                                    val mark = marksEntered.toIntOrNull()
                                                    if (mark != null && mark >= 0 && mark <= 100) {
                                                        val updatedList = subjectMarksList.toMutableList()
                                                        updatedList[index] = mark.toString()
                                                        subjectMarksList = updatedList
                                                        showMarksDialog = false
                                                    }
                                                },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Green,
                                                    contentColor = White
                                                )
                                            ) {
                                                Text(text = "Save")
                                            }
                                        },
                                        dismissButton = {
                                            TextButton(
                                                onClick = {
                                                    showMarksDialog = false
                                                }
                                            ) {
                                                Text(text = "Cancel", color = Gray2)
                                            }
                                        }

                                    )
                                }

                            }
                        }
                    }
                }
            }

            else {
                Button(
                    onClick = {
                        showSubjectEntryDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange,
                        contentColor = White
                    )
                ) {
                    Text(
                        text = "Enter your Subjects",
                        fontSize = 18.sp
                    )
                }
            }


        } else {    // for displaying message when no information is added
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(text = "Welcome!",
                    fontSize = 24.sp,
                    color = Blackish
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "No information added yet!",
                    fontSize = 18.sp,
                    color = Gray2
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        showUserDetailsDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green,
                        contentColor = White
                    )
                ) {
                    Text(
                        text = "Enter Student Details",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }

    if(showUserDetailsDialog) {
        TakeUserCredentials(
            closeClick = {
                showUserDetailsDialog = false
            },
            saveClick = {name, rollNo, year ->
                nameOfStudentInScreen = name
                rollNoInScreen = rollNo
                yearOfPassingInScreen = year
                showUserDetailsDialog = false
            }
        )
    }

    if(showSubjectEntryDialog) {
        TakeSubjectDetails(
            closeClick = {
                showSubjectEntryDialog = false
            },
            saveClick = { subjects ->
                subjectListInScreen = subjects
                subjectMarksList = List(subjects.size) { "" }
                showSubjectEntryDialog = false
            }
        )
    }

}

@Composable
fun TakeUserCredentials(
    closeClick: () -> Unit,
    saveClick: (String, String, String) -> Unit
) {

    var nameOfStudent by remember { mutableStateOf("") }
    var rollNo by remember { mutableStateOf("") }
    var yearOfPassing by remember { mutableStateOf("") }

    AlertDialog(
        title = {
            Text(text = "Enter your details", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Blackish)
                },
        text = {
            Column( // for taking user input
                modifier = Modifier.fillMaxWidth(),
            ) {
                TextField(
                    label = { Text(text = "Name", fontSize = 16.sp, color = Gray2)},
                    value = nameOfStudent,
                    onValueChange = {nameOfStudent = it},
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Gray1,
                        unfocusedContainerColor = Gray1,
                        focusedIndicatorColor = Green,
                        unfocusedIndicatorColor = Gray2,
                        focusedTextColor = Blackish,
                        unfocusedTextColor = Blackish
                    )
                )

                TextField(
                    label = { Text(text = "Roll No.", fontSize = 16.sp, color = Gray2)},
                    value = rollNo,
                    onValueChange = {rollNo = it},
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Gray1,
                        unfocusedContainerColor = Gray1,
                        focusedIndicatorColor = Green,
                        unfocusedIndicatorColor = Gray2,
                        focusedTextColor = Blackish,
                        unfocusedTextColor = Blackish
                    )
                )

                TextField(
                    label = { Text(text = "Year of passing", fontSize = 16.sp, color = Gray2)},
                    value = yearOfPassing,
                    onValueChange = {yearOfPassing = it},
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Gray1,
                        unfocusedContainerColor = Gray1,
                        focusedIndicatorColor = Green,
                        unfocusedIndicatorColor = Gray2,
                        focusedTextColor = Blackish,
                        unfocusedTextColor = Blackish
                    )
                )
            }

        },
        onDismissRequest = {
            closeClick()
        },
        confirmButton = {
            Button(
                onClick = { saveClick(nameOfStudent, rollNo, yearOfPassing) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green,
                    contentColor = White
                ),
            ) {
                Text(text = "Save")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    closeClick()
                }
            ) {
                Text(text = "Cancel", color = Gray2)
            }
        }

    )
}

@Composable
fun TakeSubjectDetails(
    closeClick: () -> Unit,
    saveClick: (List<String>) -> Unit
) {

    var subjectsEntered by remember { mutableStateOf("") } // normal string

    AlertDialog(
        title = {
            Text(text = "Enter your Subjects", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Blackish)
        },
        text = {
            Column( // for taking user input
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Enter comma-separated subjects", fontSize = 16.sp)

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    label = { Text(text = "Subjects", fontSize = 16.sp, color = Gray2)},
                    value = subjectsEntered,
                    onValueChange = {subjectsEntered = it},
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Gray1,
                        unfocusedContainerColor = Gray1,
                        focusedIndicatorColor = Green,
                        unfocusedIndicatorColor = Gray2,
                        focusedTextColor = Blackish,
                        unfocusedTextColor = Blackish
                    )
                )
            }

        },
        onDismissRequest = {
            closeClick()
        },
        confirmButton = {
            Button(
                onClick = {
                    val subjects = subjectsEntered.split(",") // this was given in the generated code, so understood this from claude
                        .map { it.trim() }
                        .filter { it.isNotEmpty() }
                    saveClick(subjects)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green,
                    contentColor = White
                ),
            ) {
                Text(text = "Save")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    closeClick()
                }
            ) {
                Text(text = "Cancel", color = Gray2)
            }
        }

    )
}

fun getPerformance(marks: Int): String {
    return when {
        marks < 40 -> "Poor"
        marks < 75 -> "Satisfactory"
        else -> "Excellent"
    }
}

fun getPerformanceColor(marks: Int): Color {
    return when {
        marks < 40 -> Orange
        marks < 75 -> Yellow
        else -> Green
    }
}