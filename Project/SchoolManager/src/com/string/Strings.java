package com.string;

public class Strings {
    // contexts
    public static final String context_insert = "INSERT";
    public static final String context_update = "UPDATE";
    
    // error messages
    // login
    public static final String uNameWrong = "User Not Found";
    public static final String pwdWrong = "Password Incorrect";
    public static final String accessDenied = "Access Denied";
    
    // messages
    public static final String accessGranted = "Access Granted";
    public static final String bookAdded = "Book Added Successfully";
    public static final String itemAdded = "Item Added Successfully";
    public static final String itemRemoved = "Item Removed Successfully";
    public static final String itemChanged = "Item Changed Successfully";
    public static final String studentAdded = "Student Added Successfully";
    public static final String studentChanged = "Student Changed Successfully";
    public static final String subjectAdded = "Subject Added Successfully";
    public static final String subjectChanged = "Subject Changed Successfully";
    public static final String clicked_home = "Clicked HOME";
    public static final String clicked_settings = "Clicked SETTINGS";
    public static final String clicked_reports = "Clicked REPORTS";
    public static final String clicked_statistics = "Clicked STATISTICS";
    public static final String clicked_notifications = "Clicked NOTIFICATIONS";
    public static final String clicked_user = "Clicked USER";
    public static final String clicked_close = "Close Clicked";
    public static final String clicked_addItem = "Clicked on add Item";
    public static final String clicked_changeItemStatus = "Clicked on change Item Status";
    public static final String clicked_searchCategory = "Clicked search Category";
    public static final String clicked_editProfile = "Clicked Edit profile";
    public static final String clicked_logOut = "Clicked Log Out";
    public static final String clicked_oK = "Clicked OK";
    public static final String clicked_cancel = "Clicked Cancel";
    public static final String clicked_addBook = "Clicked on add Book";
    public static final String clicked_updateStudent = "Clicked Update Student";
    public static final String clicked_attendance = "Clicked Attendance";
    public static final String clicked_individualReport = "Clicked Individual Report";
    public static final String created_addItemUISingleton = "addItemUISingleton created";
    public static final String created_addStaffUISingleton = "addStaffUISingleton Created";
    public static final String created_staffTableUISingleton = "staffTableUISingleton Created";
    public static final String created_staffUISingleton = "staffUISingleton Created";
    public static final String created_itemTableUISingleton = "itemTableUISingleton created";
    public static final String created_bookTableUISingleton = "bookTableUISingleton created";
    public static final String created_changeItemUISingleton = "changeItemUISingleton created";
    public static final String created_nonAcademicStaffSingleton = "nonAcademicStaffSingleton created";
    public static final String created_currentItemSingletonCreated = "currentItemSingleton created";
    public static final String created_bookSingleton = "bookSingleton created";
    public static final String created_addBookUISingleton = "addBookUISingleton created";
    public static final String created_subjectSingleton = "subjectSingleton created";
    public static final String created_studentTableUISingleton = "studentTableUISingleton created";
    public static final String created_academicStaffSingleton = "academicStaffSingleton created";
    public static final String created_examTableUISingleton = "examTableUISingleton created";
    public static final String created_examSingleton = "examSingleton created";
    public static final String created_subjectUISingleton = "subjectUISingleton created";
    public static final String created_subjectTableUISingleton = "subjectTableUISingleton created";
    public static final String created_addSubjectUISingleton = "addSubjectUISingleton created";
    public static final String created_attendanceTableUISingleton = "attendanceTableUISingleton created";
    public static final String created_attendanceSingleton = "attendanceSingleton created";
    public static final String categoryAdded = "Category Added";
    public static final String categoryUpdated = "Category Updated";
    public static final String savedCurrentItem = "Current Item Saved";
    public static final String loadedLastItem = "Last Item Loaded";
    
    
    // titles
    public static final String titles_schoolManagerAdministrator = "SchoolManager - Administrator";
    public static final String titles_addItem = "Add Item";
    public static final String titles_changeItem = "Change Item";
    public static final String titles_addCategory = "Add Category";
    public static final String titles_inventoryItems = "Inventory Items";
    public static final String titles_inventoryBooks = "Inventory Books";
    public static final String titles_editUser = "Edit User";
    public static final String titles_choosePicture = "Choose Picture";
    public static final String titles_addBook = "Add Book";
    public static final String titles_studentTable = "Student Table";
    public static final String titles_addStudent = "Add Student";
    public static final String titles_examTable = "Exam Table";
    public static final String titles_addSubject = "Add Subject";
    public static final String titles_attendanceTable = "Attendance Table";
    public static final String titles_markAttendance = "Mark Attendance";
    
    // tables
    public static final String status = "status";
    public static final String unit = "unit";
    public static final String category = "category";
    public static final String ItemCategories = "ItemCategories";
    public static final String userId = "userID";
    public static final String name = "name";
    public static final String grade = "grade";
    public static final String birthDate = "birthDate";
    public static final String registeredDate = "registeredDate";
    public static final String phone = "phone";
    public static final String address = "address";
    public static final String email = "email";
    public static final String resourceID = "resourceID";
    public static final String tablePlaceholderDefault = "Search | Browse | Add | Change";
    public static final String tablePlaceholderNoItemsFound = "No Items Found";
    
    // inventory
    public static final String Inventory = "Inventory";
    public static final String supplyDate = "supplyDate";
    public static final String updatedOn = "updatedOn";
    public static final String status_NEW = "NEW";
    public static final String status_Using = "Using";
    public static final String status_Expired = "Expired";
    public static final String status_Repairable = "Repairable";
    public static final String status_Replacable = "Replacable";
    public static final String status_OutOfStock = "Out of Stock";
    public static final String category_stationery = "Stationery";
    
    // items
    public static final String Item = "Item";
    public static final String Items = "Items";
    public static final String itemID = "itemID";
    public static final String description = "description";
    public static final String quantity = "quantity";
    public static final String cost = "cost";
    public static final String expireDate = "expireDate";
    
    // adding items
    public static final String invalidDescription = "Please use 0-50 chars for description";
    public static final String invalidExpireDate = "Please enter valid date in expireDate";
    public static final String invalidUpdatedOn = "Please enter valid date item updated";
    public static final String invalidCost = "Please enter a valid value for cost";
    public static final String invalidQuantity = "Please enter a valid value for quantity";
    public static final String invalidCategory = "Enter a valid category first";
    public static final String invalidFileSelected = "Invalid File Selected";
    public static final String notSelectedStatus = "Please select a value for status";
    public static final String notSelectedDate = "Please select a value for date";
    public static final String notSelectedUnit = "Please select a value for unit";
    public static final String notSelectedCategory = "Please select a value for category";
    public static final String bookNotAdded = "Book Not Added";
    public static final String itemNotAdded = "Item Not Added";
    public static final String itemNotRemoved = "Item Not Removed";
    public static final String categoryNotAdded = "Error. Category Not Added";
    public static final String categoryNotUpdated = "Error. Category Not Updated";
    public static final String notSpecified = "Not specified";
    public static final String notEnteredSearch = "Enter item detail to search for";
    
    // undo updated item
    public static final String itemNotReset = "Item Not Reset";
    public static final String itemResetDone = "Item Reset Done";
    
    // add book
    public static final String invalidTitle = "Please enter a valid book title";
    public static final String invalidLanguage = "Please enter a valid language";
    public static final String invalidBookID = "Please enter a valid bookID";
    public static final String invalidAuthor = "Please enter a valid author";
    public static final String invalidCopies = "Please enter valid number of copies";
    public static final String invalidPrice = "Please enter valid Price";
    
    // edit items
    public static final String notSelectedItem = "Select an Item first from the table";
    
    // books
    public static final String Book = "Book";
    public static final String Books = "Books";
    public static final String bookID = "bookID";
    
    // staff
    public static final String Staff = "Staff";
    public static final String designation = "designation";
    public static final String qualification = "qualification";
    
    // nonAcademic
    public static final String NonAcademicStaff = "NonAcademicStaff";
    public static final String Inventory_Manager = "Inventory Manager";
    public static final String Student_Manager = "Student Manager";
    public static final String Exam_Manager = "Exam Manager";
    public static final String Attendance_Manager = "Attendance Manager";
    public static final String Result_Manager = "Result Manager";
    public static final String Staff_Manager = "Staff Manager";
    public static final String Library_Manager = "Library Manager";
    public static final String Subject_Manager = "Subject Manager";
    
    // academic
    public static final String AcademicStaff = "AcademicStaff";
    
    // URLs
    // icons
    
    // schoolmanager
    public static final String schoolManagerIconURL = "img/schoolmanager/school_filled_100px.png";
    
    // inventory manager
    public static final String inventoryIconURL = "img/schoolmanager/inventory_flow_100px.png";
    public static final String inventoryItemsTableIconURL = "img/schoolmanager/product_filled_100px.png";
    public static final String addItemIconURL = "img/schoolmanager/add_shopping_cart_filled_100px(secondary).png";
    public static final String addCategoryIconURL = "img/schoolmanager/joyent_filled_100px.png";
    public static final String changeItemStatusIconURL = "img/schoolmanager/Update Left Rotation_100px(secondary).png";
    public static final String addBookIconURL = "/img/schoolmanager/books_100px.png";
    
    // subject manager
    public static final String subjectIconURL = "img/schoolmanager/subject_100px.png";
    
    // attendance manager
    public static final String attendanceIconURL = "img/schoolmanager/attendance_filled_100px.png";
    
    // exam manager
    public static final String Exam = "Exam";
    public static final String examIconURL = "img/schoolmanager/exam_filled_100px.png";
    
    // student manager
    public static final String Student = "Student";
    public static final String studentIconURL = "img/schoolmanager/student_filled_100px.png";
    
    // staff manager
    public static final String staffIconURL = "img/schoolmanager/staff_filled_100px.png";
    
    // results manager
    public static final String resultsIconURL = "img/schoolmanager/result_filled_100px.png";
    
    // library manager
    public static final String libraryIconURL = "img/schoolmanager/book_shelf_filled_100px.png";
    
    // admin
    public static final String adminIconURL = "img/schoolmanager/verified_account_filled_100px.png";
    
    // edit user
    public static final String editUserIconURL = "img/schoolmanager/edit_user_filled_100px.png";
    public static final String imageDisplayPictureURL = "img/user/";
    public static final String imageInventoryManagerDisplayPictureURL = "img\\user\\shibe_349px.jpg";
    public static final String editUserInitialDirecory = "C:\\";
    
    // hover
    public static final String hoverBookIconPURL = "img/schoolmanager/open_book_filled_100px.png";
    public static final String hoverItemIconPURL = "img/schoolmanager/product_filled_100px.png";
    public static final String hoverItemIconSURL = "img/schoolmanager/product_filled_100px(secondary).png";
    public static final String hoverBookIconSURL = "img/schoolmanager/open_book_filled_100px(secondary).png";
    
    // student grades
    public static final String grade_6  = "Grade 6";
    public static final String grade_7  = "Grade 7";
    public static final String grade_8  = "Grade 8";
    public static final String grade_9  = "Grade 9";
    public static final String grade_10 = "Grade 10";
    public static final String grade_11 = "Grade 11";
    public static final String grade_12 = "Grade 12";
    public static final String grade_13 = "Grade 13";
    
    // student religions
    public static final String religion_buddhism = "Buddhism";
    public static final String religion_islam = "Islam";
    public static final String religion_hindu = "Hindu";
    public static final String religion_christianity = "Christianity";
    public static final String religion_catholicism = "Catholicism";
    
    // add student/staff messages
    public static final String invalidStudentID = "Please enter a valid studentID";
    public static final String invalidName = "Please enter a valid name";
    public static final String invalidGrade = "Please select a starting grade of the student";
    public static final String invalidBirthDate = "Please give birthdate";
    public static final String invalidAddress = "Please enter a valid address";
    public static final String invalidEmail = "Please enter a valid email";
    public static final String invalidPhone = "Please enter a valid phone number";
    public static final String invalidReligion = "Please select religion of the student";
    public static final String invalidClass = "Please select another class for the new student";
    public static final String notSelectedClass = "Please select a class for the new student";
    public static final String notavailable = "not available";
    public static final String available = "available";
    
    // student classes
    public static final String class_A = "A";
    public static final String class_B = "B";
    public static final String class_C = "C";
    public static final String class_D = "D";
    
    // add staff messages
    public static final String invalidStaffID = "Please enter a valid staffID";
    public static final String notSelectedStaff = "Please select a staff type";
    public static final String invalidDesignation = "Please enter a designation";
    
    // staff table labels
    public static final String staffAdded = "Staff Member Added Successfully";
    public static final String staffChanged = "Staff Member Changed Successfully";
    
    // exam
    public static final String examID = "examID";
    public static final String subjectID = "subjectID";
    public static final String year = "year";
    public static final String term = "term";
    public static final String startTime = "startTime";
    public static final String endTime = "endTime";
    public static final String examDate = "examDate";
    
    // exam terms
    public static final String term_1 = "1";
    public static final String term_2 = "2";
    public static final String term_3 = "3";
    
    // add exam
    public static final String invalidExamID = "Please enter a valid examID";
    public static final String invalidExamGrade = "Please select a grade";
    public static final String invalidExamSubject = "Please select a subject";
    public static final String invalidExamYearA = "Please enter an exam year";
    public static final String invalidExamYearB = "Please enter an valid exam year";
    public static final String invalidTerm = "Please select a exam term";
    public static final String invalidStartTime = "Please enter a valid start time";
    public static final String invalidEndTime = "Please enter a valid end time";
    public static final String invalidExamDate = "Please enter a valid exam date";
    public static final String examAdded = "Exam Added Successfully";
    public static final String examChanged = "Exam Changed Successfully";
    
    // exam statuses
    public static final String status_exam_ahead = "Ahead";
    public static final String status_exam_marked = "Marked";
    public static final String status_exam_finallized = "Finallized";
    public static final String status_exam_canceled = "Canceled";

    // subject
    public static final String Subject = "Subject";
    public static final String subject = "subject";
    public static final String SubjectName = "SubjectName";
    public static final String teacher = "teacher";
    
    // add subject
    public static final String invalidSubjectID = "Please enter a valid subjectID";
    public static final String invalidSubjectName = "Please enter a valid subject name";
    public static final String invalidSubjectGrade = "Please select a grade for subject";
    
    // mark attendance
    public static final String studentID = "studentID";
    public static final String studentName = "studentName";
    public static final String presentAbsent = "presentAbsent";
    public static final String invalidAttendanceGrade = "Please select a grade";
    public static final String userID = "userID";
    public static final String date = "date";
    
    // exam years
    public static final String year_2018 = "2018";
    public static final String year_2019 = "2019";
    public static final String year_2020 = "2020";
    
    // exam table
    public static final String invalidYear = "Please select a year first";
    
    // book table
    public static final String title = "title";
    public static final String language = "language";
    public static final String author = "author";
    public static final String price = "price";
    public static final String copies = "copies";
    public static final String bookRemoved = "Book Removed Successfully";
    public static final String bookNotRemoved = "Book Not Removed";
    public static final String invalidCategories = "Please select a category first";
    public static final String tablePlaceholderNoBooksFound = "No Books Found";
    public static final String notEnteredSearchBook = "Enter book detail to search for";

    // student table
    public static final String invalidStudentClass = "Please select a class of student";
    public static final String tablePlaceholderNoStudentsFound = "No Students Found";
    public static final String notEnteredSearchStudent = "Enter student detail to search for";
    
    // radio
    public static final String NonAcademic = "NonAcademic";
    public static final String Academic = "Academic";
    public static final String invalidSubject = "Please select a subject first";
    
    // attendance
    public static final String notSelectedAttendanceClass = "Please select a class";
    
    // item
    public static final String itemUpdated = "Item Updated";
    public static final String itemNotChanged = "Item Not Updated";
    public static final String context_remove = "REMOVE";
    
    // months
    public static final String month_jan = "January";
    public static final String month_feb = "February";
    public static final String month_mar = "March";
    public static final String month_apr = "April";
    public static final String month_may = "May";
    public static final String month_jun = "June";
    public static final String month_jul = "July";
    public static final String month_aug = "August";
    public static final String month_sep = "September";
    public static final String month_oct = "October";
    public static final String month_nov = "November";
    public static final String month_dec = "December";
    
    // month message
    public static final String invalidMonth = "Select a month first";
    
    // staff
    public static final String invalidStaffType = "Select a staff type first";
    
    // attendance
    public static final String invalidAttendanceClass = "Please select a class";
    public static final String created_attendanceClassesMarkedUISingleton = "attendanceClassesMarkedUISingleton created";
    
    // themes
    public static final String themeDefault = "selected Default";
    public static final String themeSecondary = "selected Secondary";
    public static final String themeSystem = "selected System";
    public static final String themeDark = "selected Dark";
    
    // theme styles inventory
    public static final String themeDefaultStyleInventory = "-fx-primary: teal;";
    public static final String themeSecondaryStyleInventory = "-fx-primary: steelBlue;";
    public static final String themeSystemStyleInventory = "-fx-primary: orange;";
    public static final String themeDarkStyleInventory = "-fx-primary: #333333;";
    public static final String themeGradientPrimaryInventory = "-fx-primarygradient: #008086";
    public static final String themeGradientSecondaryInventory = "-fx-primarygradient: #333333";
    
    // attendance
    public static final String select = "select";
    public static final String tablePlaceholderAttendance = "Select a grade,class to mark attendance";
    public static final String tablePlaceholderAttendanceNotYetMarked = "Not yet marked for this class";
    public static final String attendanceUpdatedSuccessfully = "Attendance Updated Successfully";
    public static final String attendanceNotUpdated = "Attendance Not Updated";
    public static final String tablePlaceholderBrowse = "Select grade,class,date of a class";
    public static final String noRecordsFoundForThisSelection = "No records found for this selection";
    public static final String clicked_update = "Clicked Update";
    
    // user profile update
    public static final String updateUserSuccessful = "User Update Successful";
    
    // all css
    public static final String css_setTextColorPrimary = "-fx-text-fill: -fx-primary;";
    
    // search staff
    public static final String tablePlaceholderNoStaffFound = "No Staff Members Found";
    public static final String notEnteredStaffSearch = "Enter some detail to search for";
    
    // update staff
    public static final String savedCurrentUser = "Saved Current User";
    public static final String created_updateStaffUISingleton = "Created updateStaffUISingleton";
    public static final String titles_updateStaff = "Update Staff";
    public static final String notSelectedStaffMember = "Staff Member Not Selected";
    public static final String staffUpdated = "Staff Updated Successfully";
    public static final String notUsableDesignation = "Designation Not Usable";
    public static final String tablePlaceholderStaffUpdatedSuccessfully = "Staff Updated Successfully";
    
    // clear
    public static final String clear = "";
    
    // update subject
    public static final String savedCurrentSubject = "Saved Current Subject";
    public static final String created_updateSubjectUISingleton = "Created updateSubjectUISingleton";
    public static final String notSelectedSubject = "Subject Not Selected";
    public static final String titles_updateSubject = "Update Subject";
    public static final String invalidSubjectTeacher = "Please Select a Teacher for the Subject";
    public static final String subjectUpdated = "Subject Updated Successfully";
    public static final String subjectNotUpdated = "Subject Not Updated";
    public static final String tablePlaceholderSubjectUpdatedSuccessfully = "Subject Updated Successfully";
    public static final String teacherNotAvailable = "Teacher not available for assign";
    public static final String assignedTeacherSubject = "Teacher assigned for the subject";
    public static final String notAssignedTeacherSubject = "Teacher not assigned for the subject";
    
    // update student
    public static final String created_updateStudentUISingleton = "Created updateStudentUISingleton";
    public static final String notSelectedStudent = "Select a Student First";
    public static final String titles_updateStudent = "Update Student";
    public static final String studentNotUpdated = "Student Not Updated";
    public static final String studentUpdated = "Student Updated";
    public static final String tablePlaceholderStudentUpdatedSuccessfully = "Student Updated Successfully";
    
    // results
    public static final String clicked_results = "Clicked Results";
    public static final String created_resultTableUISingleton = "Created resultTableUISingleton";
    public static final String titles_resultTable = "Result Table";
    
    // exam update
    public static final String created_updateExamUISingleton = "Created updateExamUISingleton";
    public static final String invalidExamStatusWarning = "Please Select an Exam which Unmarked";
    public static final String notSelectedExam = "Please Select an Exam first";
    public static final String titles_updateExam = "Update Exam";
    
    // staff report
    public static final String invalidType = "Please select a staff type first";
    
    // result
    public static final String resultID = "resultID";
    public static final String created_resultSingleton = "Created resultSingleton";
    public static final String result = "result";
    public static final String resultAdded = "Result Added";
    public static final String resultChanged = "Result Updated";
    
    // attendance individual report
    public static final String titles_individualAttendanceReport = "Individual Report";
    public static final String notSelectedAttendanceMember = "Student not selected";
    public static final String examRemoved = "Exam Removed Successfully";
    
    
    
    
}
