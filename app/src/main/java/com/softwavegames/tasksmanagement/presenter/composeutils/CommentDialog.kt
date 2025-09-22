package com.softwavegames.tasksmanagement.presenter.composeutils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.softwavegames.tasksmanagement.R
import com.softwavegames.tasksmanagement.ui.theme.AmsiProBold
import com.softwavegames.tasksmanagement.ui.theme.AmsiProRegular
import com.softwavegames.tasksmanagement.ui.theme.Beige
import com.softwavegames.tasksmanagement.ui.theme.Green

@Composable
fun CommentDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    onCancel: () -> Unit,
    initialComment: String = ""
) {
    if (isVisible) {
        var comment by remember { mutableStateOf(initialComment) }
        val keyboardController = LocalSoftwareKeyboardController.current
        
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Beige)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.add_comment),
                        fontFamily = AmsiProBold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    
                    Text(
                        text = stringResource(R.string.comment_description),
                        fontFamily = AmsiProRegular,
                        fontSize = 14.sp,
                        color = Color.Black.copy(alpha = 0.7f)
                    )
                    
                    OutlinedTextField(
                        value = comment,
                        onValueChange = { comment = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        placeholder = {
                            Text(
                                text = stringResource(R.string.comment_placeholder),
                                fontFamily = AmsiProRegular,
                                color = Color.Black.copy(alpha = 0.5f)
                            )
                        },
                        textStyle = TextStyle(
                            fontFamily = AmsiProRegular,
                            fontSize = 14.sp,
                            color = Color.Black
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Green,
                            unfocusedBorderColor = Color.Black.copy(alpha = 0.3f),
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                if (comment.isNotBlank()) {
                                    onConfirm(comment.trim())
                                } else {
                                    onCancel()
                                }
                            }
                        ),
                        maxLines = 4
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = onCancel,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black.copy(alpha = 0.1f)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.skip),
                                fontFamily = AmsiProBold,
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                        
                        Button(
                            onClick = {
                                if (comment.isNotBlank()) {
                                    onConfirm(comment.trim())
                                } else {
                                    onCancel()
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Green
                            ),
                            shape = RoundedCornerShape(8.dp),
                            enabled = comment.isNotBlank()
                        ) {
                            Text(
                                text = stringResource(R.string.add_comment_button),
                                fontFamily = AmsiProBold,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun CommentDialogPreview() {
    CommentDialog(
        isVisible = true,
        onDismiss = {},
        onConfirm = {},
        onCancel = {},
        initialComment = "This is a test comment."
    )
}