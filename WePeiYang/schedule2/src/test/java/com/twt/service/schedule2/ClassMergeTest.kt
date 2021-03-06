package com.twt.service.schedule2

import com.twt.service.schedule2.extensions.findConflict
import com.twt.service.schedule2.extensions.flatDay
import com.twt.service.schedule2.extensions.mergeCourses
import com.twt.service.schedule2.extensions.termStart
import com.twt.service.schedule2.model.*
import com.twt.service.schedule2.model.custom.CustomCourse
import com.twt.service.schedule2.model.custom.CustomCourseManager
import com.twt.wepeiyang.commons.experimental.network.CommonBody
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

/**
 * 纯办公网课表的课程表Merge情况
 */
class ClassMergeTest {

    val dayOfInt = 86400L

    val tjuClassTable: AbsClasstableProvider = TableProvider.tjuClassTable
    val mergedClassTableProvider: AbsClasstableProvider

    init {
        val arrange1 = Arrange("单双周",5,6,3,"学校")
        val week = Week(1,18)
        val course1 = createCourse("给女朋友买零食","开心的我", listOf(arrange1),week,"必须要去.... ")

        val customCourseList = mutableListOf<CustomCourse>(course1).asSequence()
                .map(CustomCourseManager.customCourseMapper)
                .toList()
        val classtable = Classtable(courses = customCourseList,termStart = termStart)
        val realClasstableProvider = CommonClassTable(classtable)

        mergedClassTableProvider = MergedClassTableProvider(TableProvider.tjuClassTable,TableProvider.auditClasstable, realClasstableProvider)
    }

    @Test
    fun testMergeWeek4Day2() {
        val currentUnix: Long = 1522082777L // 4周 周二
        val todayCourses = tjuClassTable.getCourseByDay(currentUnix)
        val mergedClasses = tjuClassTable.mergeCourses(todayCourses, dayUnix = currentUnix)
        Assert.assertEquals(5, mergedClasses.size)
        Assert.assertEquals(1, mergedClasses.filter { it.next.size > 0 }.size)

        val flat = mergedClasses.flatDay(2)
        assertEquals(2, flat.filter { it.coursename == "空" }.size)

        println("我永远喜欢十元")
    }

    @Test
    fun testMergeWeek4Day3() {
        val currentUnix: Long = 1522082777L + dayOfInt // 4周 周二
        val todayCourses = tjuClassTable.getCourseByDay(currentUnix)
        val mergedClasses = tjuClassTable.mergeCourses(todayCourses, dayUnix = currentUnix)
        Assert.assertEquals(5, mergedClasses.size)
        Assert.assertEquals(3, mergedClasses.filter { it.next.size > 0 }.size)

        val flat = mergedClasses.flatDay(3)
        assertEquals(0, flat.filter { it.coursename == "空" }.size)

    }

    @Test
    fun testMergeWeek4Day1() {
        val currentUnix: Long = 1522082777L - dayOfInt // 4周 周1
        val todayCourses = tjuClassTable.getCourseByDay(currentUnix)
        val mergedClasses = tjuClassTable.mergeCourses(todayCourses, dayUnix = currentUnix)
        Assert.assertEquals(4, mergedClasses.size)
        Assert.assertEquals(1, mergedClasses.filter { it.next.size > 0 }.size)

        val flat = mergedClasses.flatDay(1)
        assertEquals("空", flat[0].coursename)
        assertEquals("空", flat[1].coursename)
        assertEquals(4, flat.filter { it.coursename == "空" }.size)

    }

    @Test
    fun testMergeWeek4Day4() {
        val currentUnix: Long = 1522082777L + 2 * dayOfInt // 4周 周4
        val todayCourses = tjuClassTable.getCourseByDay(currentUnix)
        val mergedClasses = tjuClassTable.mergeCourses(todayCourses, dayUnix = currentUnix)
        Assert.assertEquals(5, mergedClasses.size)
        Assert.assertEquals(1, mergedClasses.filter { it.next.size > 0 }.size)
    }

    @Test
    fun testMergeWeek4Day5() {
        val currentUnix: Long = 1522082777L + 3 * dayOfInt // 4周 周5
        val todayCourses = tjuClassTable.getCourseByDay(currentUnix)
        val mergedClasses = tjuClassTable.mergeCourses(todayCourses, dayUnix = currentUnix)
        Assert.assertEquals(1, mergedClasses.size)
        Assert.assertEquals(1, mergedClasses.filter { it.next.size > 0 }.size)
    }

    @Test
    fun testMergeWeek4Day6() {
        val currentUnix: Long = 1522082777L + 4 * dayOfInt // 4周 周6
        val todayCourses = tjuClassTable.getCourseByDay(currentUnix)
        val mergedClasses = tjuClassTable.mergeCourses(todayCourses, dayUnix = currentUnix)
        Assert.assertEquals(1, mergedClasses.size)
        Assert.assertEquals(0, mergedClasses.filter { it.next.size > 0 }.size)
    }

    @Test
    fun testMergeWeek4Day7() {
        val currentUnix: Long = 1522082777L + 5 * dayOfInt // 4周 周7
        val todayCourses = tjuClassTable.getCourseByDay(currentUnix)
        val mergedClasses = tjuClassTable.mergeCourses(todayCourses, dayUnix = currentUnix)
        Assert.assertEquals(1, mergedClasses.size)
        Assert.assertEquals(0, mergedClasses.filter { it.next.size > 0 }.size)
    }

    val week5Day1 = 1522602061L // 第五周第一天
    @Test
    fun testWeek5Day1() {
        val currentUnix = week5Day1
        val todayCourse = tjuClassTable.getCourseByDay(currentUnix)
        val mergedClasses = tjuClassTable.mergeCourses(todayCourse, dayUnix = currentUnix)
        assertEquals(4, mergedClasses.size)
        assertEquals(1, mergedClasses.filter { it.next.size > 0 }.size)
    }

    @Test
    fun testWeek5Day2() {
        val currentUnix = week5Day1 + dayOfInt
        val todayCourse = tjuClassTable.getCourseByDay(currentUnix)
        val mergedClasses = tjuClassTable.mergeCourses(todayCourse, dayUnix = currentUnix)
        assertEquals(5, mergedClasses.size)
        assertEquals(1, mergedClasses.filter { it.next.size > 0 }.size)
    }

    @Test
    fun testWeek5Day3() {
        val currentUnix = week5Day1 + dayOfInt * 2
        val todayCourse = tjuClassTable.getCourseByDay(currentUnix)
        val mergedClasses = tjuClassTable.mergeCourses(todayCourse, dayUnix = currentUnix)
        assertEquals(5, mergedClasses.size)
        assertEquals(3, mergedClasses.filter { it.next.size > 0 }.size)
    }

    @Test
    fun testWeek5Day4() {
        val currentUnix = week5Day1 + dayOfInt * 3
        val todayCourse = tjuClassTable.getCourseByDay(currentUnix)
        val mergedClasses = tjuClassTable.mergeCourses(todayCourse, dayUnix = currentUnix)
        assertEquals(5, mergedClasses.size)
        assertEquals(1, mergedClasses.filter { it.next.size > 0 }.size)
    }

    @Test
    fun testConflict() {
        val currentUnix = week5Day1 + dayOfInt
        val todayCourse = tjuClassTable.getCourseByDay(currentUnix)
        val mergedClasses = tjuClassTable.mergeCourses(todayCourse, dayUnix = currentUnix)
        val course = mergedClasses[2]
        println(course)
        val conflictCouse = tjuClassTable.getCourseByWeek(3).findConflict(course)
        println(conflictCouse)
        assertNotNull(conflictCouse)
    }

    @Test fun testTotalMerge() {
        val currentUnix = week5Day1 + dayOfInt * 2
        val todayCourse = mergedClassTableProvider.getCourseByDay(currentUnix)
        assertEquals(5, todayCourse.size)
        assertEquals(5, todayCourse.filter { it.next.size > 0 }.size)
    }

    fun createCourse(name: String, teacher: String, arrange: List<Arrange>, week: Week,ext: String = ""): CustomCourse {
        val customCourse = CustomCourse(name, teacher,ext, arrange, week)
        return customCourse
    }

}