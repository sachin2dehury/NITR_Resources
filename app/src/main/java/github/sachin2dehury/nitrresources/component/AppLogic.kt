package github.sachin2dehury.nitrresources.component

import github.sachin2dehury.nitrresources.core.DocDetails

object AppLogic {
    fun listSelector(item: Int): List<String> {
        return when (item) {
            AppCore.STREAM_LIST -> AppCore.streamList
            AppCore.YEAR_LIST -> AppCore.yearList
            AppCore.B_ARCH_LIST -> AppCore.bArchList
            AppCore.B_TECH_LIST -> AppCore.bTechList
            AppCore.M_TECH_LIST -> AppCore.mTechList
            AppCore.MSC_LIST -> AppCore.mscList
            AppCore.INT_MSC_LIST -> AppCore.intMscList
            else -> AppCore.noList
        }
    }

    fun listPredictor(item: Int, position: Int): Int {
        return when (item) {
            AppCore.STREAM_LIST -> AppCore.B_ARCH_LIST + position
            in AppCore.B_ARCH_LIST..AppCore.M_TECH_LIST -> AppCore.YEAR_LIST
            else -> AppCore.YEAR_LIST
        }
    }

    fun dataSetter(item: Int, position: Int) {
        when (item) {
            AppCore.STREAM_LIST -> {
                AppCore.streamYrs = AppCore.streamWiseYearList[position]
                AppCore.stream = AppCore.streamList[position]
            }
            AppCore.YEAR_LIST -> {
                if (position == 0) {
                    when (AppCore.stream) {
                        AppCore.streamList[1] -> AppCore.branch = "All"
                        AppCore.streamList[2] -> AppCore.branch = "All"
                    }
                }
                AppCore.year = AppCore.yearList[position]
            }
            AppCore.B_ARCH_LIST -> AppCore.branch = AppCore.bArchList[position]
            AppCore.B_TECH_LIST -> AppCore.branch = AppCore.bTechList[position]
            AppCore.M_TECH_LIST -> AppCore.branch = AppCore.mTechList[position]
            AppCore.MSC_LIST -> AppCore.branch = AppCore.mscList[position]
            AppCore.INT_MSC_LIST -> AppCore.branch = AppCore.intMscList[position]
        }
    }

    fun pageSelector(position: Int): MutableMap<String, DocDetails> {
        return when (AppCore.NOTES_LIST + position) {
            AppCore.NOTES_LIST -> AppCore.notes
            AppCore.ASSIGNMENT_LIST -> AppCore.assignment
            AppCore.SLIDES_LIST -> AppCore.slides
            AppCore.LAB_LIST -> AppCore.lab
            else -> AppCore.trash
        }
    }
}