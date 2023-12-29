package com.example.myapplication.businessLogic.repository

import com.example.myapplication.api.model.ReportRemote

interface ReportRepository {
    suspend fun getReportData(dateFrom: Long, dateTo: Long): ReportRemote
}