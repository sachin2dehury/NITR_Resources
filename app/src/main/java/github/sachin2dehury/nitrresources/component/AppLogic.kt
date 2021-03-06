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
                AppCore.currentStream = AppCore.streamList[position]
            }
            AppCore.YEAR_LIST -> AppCore.currentYear = AppCore.yearList[position]
            AppCore.B_ARCH_LIST -> AppCore.currentBranch = AppCore.bArchList[position]
            AppCore.B_TECH_LIST -> AppCore.currentBranch = AppCore.bTechList[position]
            AppCore.M_TECH_LIST -> AppCore.currentBranch = AppCore.mTechList[position]
            AppCore.MSC_LIST -> AppCore.currentBranch = AppCore.mscList[position]
            AppCore.INT_MSC_LIST -> AppCore.currentBranch = AppCore.intMscList[position]
        }
    }

    fun mapSelector(item: Int): List<String> {
        return when (item) {
            AppCore.STREAM_LIST -> AppCore.streamMap
            AppCore.YEAR_LIST -> AppCore.yearMap
            AppCore.B_ARCH_LIST -> AppCore.bArchMap
            AppCore.B_TECH_LIST -> AppCore.bTechMap
            AppCore.M_TECH_LIST -> AppCore.mTechMap
            AppCore.MSC_LIST -> AppCore.mscMap
            AppCore.INT_MSC_LIST -> AppCore.intMscMap
            else -> AppCore.noList
        }
    }

    fun pageSelector(position: Int): MutableMap<String, DocDetails> {
        return when (AppCore.NOTES_LIST + position) {
            AppCore.NOTES_LIST -> AppCore.notes
            AppCore.BOOK_LIST -> AppCore.books
            AppCore.SLIDES_LIST -> AppCore.slides
            AppCore.ASSIGNMENT_LIST -> AppCore.assignment
            AppCore.LAB_LIST -> AppCore.lab
            else -> AppCore.trash
        }
    }

    fun pathSetter(item: Int): String {
        var currentStream = AppCore.currentStream
        var currentBranch = AppCore.currentBranch
        when (AppCore.currentYear) {
            AppCore.yearList[0] -> {
                when (AppCore.currentStream) {
                    AppCore.streamList[1], AppCore.streamList[2] -> {
                        currentBranch = "All"
                        currentStream = AppCore.streamList[1]
                    }
                }
            }
        }
        return "${AppCore.COLLEGE}/${currentStream}/${AppCore.currentYear}/${currentBranch}/${AppCore.pageList[item]}"
    }

    fun commonPathSetter(item: Int): String {
        return "${AppCore.COLLEGE}/${AppCore.currentStream}/${AppCore.currentYear}/${AppCore.COMMON}/${AppCore.pageList[item]}"
    }

    fun clearList() {
        AppCore.notes.clear()
        AppCore.assignment.clear()
        AppCore.slides.clear()
        AppCore.lab.clear()
        AppCore.books.clear()
    }
}