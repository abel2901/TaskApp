package com.company.task.constants

class DataBaseConstants {
    object USER {
        val TABLE_NAME = "user"

        object COLUMNS {
            val ID = "id"
            val NAME = "name"
            val EMAIL = "email"
            val PASSWORD = "password"
        }
    }

    object PRIORITY {
        val TABLE_NAME = "PRIORITY"

        object COLUMNS {
            val ID = "id"
            val DESCRIPTION = "description"
        }
    }

    object TASK {
        val TABLE_NAME = "TASK"

        object COLUMNS {
            val ID = "id"
            val USERID = "userid"
            val PRIORITYID = "priorityId"
            val DESCRIPTION = "description"
            val COMPLETE = "complete"
            val DUEDATE = "duedate"
        }
    }
}
