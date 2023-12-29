package com.example.myapplication.businessLogic.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.model.ServiceWithCount
import com.example.myapplication.businessLogic.repository.ReportRepository
import kotlinx.coroutines.launch
import java.util.Date

class ReportViewModel(private val reportRepository: ReportRepository): MyViewModel() {
    val dateFrom = mutableStateOf(0L)
    val dateTo = mutableStateOf(Date().time)

    val avgSum = mutableStateOf(0.0)
    val totalEarn = mutableStateOf(0.0)
    val countOrder = mutableStateOf(0)
    var serviceList = MutableLiveData<List<ServiceWithCount>>()

    fun updateReportData(dateFrom: Long, dateTo: Long) {
        viewModelScope.launch {
            val report = reportRepository.getReportData(dateFrom, dateTo)
            countOrder.value = report.countOrder
            avgSum.value = report.avgCheck!!
            totalEarn.value = report.totalEarn!!
            serviceList.value = report.serviceList
        }
    }
}