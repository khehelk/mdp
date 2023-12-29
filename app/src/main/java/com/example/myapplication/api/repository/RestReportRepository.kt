package com.example.myapplication.api.repository

import com.example.myapplication.api.ServerService
import com.example.myapplication.api.model.ReportRemote
import com.example.myapplication.businessLogic.repository.ReportRepository

class RestReportRepository(private var service: ServerService) : ReportRepository {
    override suspend fun getReportData(dateFrom: Long, dateTo: Long): ReportRemote {
        return service.getReport(dateFrom, dateTo)
    }
}