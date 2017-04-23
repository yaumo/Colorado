import request from 'request'
var host = window.location.origin
var url = host +'/api'
var options = {}

//TODO: refactor Code

//Student
exports.storeStudent = (student, callback) => {
  options.json = student
  options.url = url + '/student'
  options.method = 'POST'
  console.log('POST Student with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.getStudent = (key, callback) => {
  options.url = url + '/student/' + key
  options.method = 'GET'
  console.log('GET Student with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.updateStudent = (student, callback) => {
  options.json = student
  options.url = url + '/student'
  options.method = 'POST'
  console.log('Update Student with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.deleteStudent = (key, callback) => {
  options.url = url + '/student/' + key
  options.method = 'DELETE'
  console.log('DELETE Student with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

//lecturer
exports.storeLecturer = (lecturer, callback) => {
  options.json = lecturer
  options.url = url + '/lecturer'
  options.method = 'POST'
  console.log('POST Lecturer with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.updateLecturer = (lecturer, callback) => {
  options.json = lecturer
  options.url = url + '/lecturer'
  options.method = 'POST'
  console.log('Update Lecturer with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.getLecturer = (key, callback) => {
  options.url = url + '/lecturer/' + key
  options.method = 'GET'
  console.log('GET Lecturer with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.deleteLecturer = (key, callback) => {
  options.url = url + '/lecturer/' + key
  options.method = 'DELETE'
  console.log('DELETE Lecturer with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.addLecturerToCourse = (lecturerUsername, courseName, callback) => {
  options.url = url + '/lecturer/' + lecturerUsername + '/course/' + courseName
  options.method = 'POST'
  console.log('Add Lecturer to Course with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.deleteLecturerFromCourse = (lecturerUsername, courseName, callback) => {
  options.url = url + '/lecturer/' + lecturerUsername + '/course/' + courseName
  options.method = 'DELETE'
  console.log('DELETE Lecturer with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.getAllLecturer = (callback) => {
  options.url = url + '/lecturer/'
  options.method = 'GET'
  console.log('GET All Lecturer with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.getAllTasksFromLecturer = (callback) => {
  options.url = url + '/task/lecturer'
  options.method = 'GET'
  console.log('GET all Tasks with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.getAllCoursesFromLecturer = (callback) => {
  options.url = url + '/course/lecturer'
  options.method = 'GET'
  console.log('GET All Courses from Lecturer with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (body.error){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

//task
exports.storeTask = (task, callback) => {
  options.json = task
  options.url = url + '/task'
  options.method = 'POST'
  console.log('POST Task with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.updateTask = (task, callback) => {
  options.json = task
  options.url = url + '/task/update'
  options.method = 'POST'
  console.log('Update Task with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.getTask = (key, callback) => {
  options.url = url + '/task/' + key
  options.method = 'GET'
  console.log('GET Task with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (body.error){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.deleteTaskFromLecturer = (excerciseName, lecturerUsername , callback) => {
  options.url = url + '/task/' + excerciseName + '/lecturer/' + lecturerUsername
  options.method = 'DELETE'
  console.log('DELETE Task with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (body.error){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.deleteTaskFromCourse = (excerciseName, courseName , callback) => {
  options.url = url + '/task/' + excerciseName + '/course/' + courseName
  options.method = 'DELETE'
  console.log('DELETE Task with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (body.error){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.addTaskToCourse = (excerciseName, courseName ,callback) => {
  options.url = url + '/task/' + excerciseName + '/course/' + courseName
  options.method = 'POST'
  console.log('Add Task to Course with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

//course
exports.storeCourse = (course, callback) => {
  options.json = course
  options.url = url + '/course'
  options.method = 'POST'
  console.log('POST Course with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.updateCourse = (course, callback) => {
  options.json = course
  options.url = url + '/course/update'
  options.method = 'POST'
  console.log('Update Course with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.deleteCourse = (key, callback) => {
  options.url = url + '/course/' + key
  options.method = 'DELETE'
  console.log('DELETE Course with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.getCourse = (key, callback) => {
  options.url = url + '/course/' + key
  options.method = 'GET'
  console.log('GET Course with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (body.error){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.getAllTasksFromCourse = (key, callback) => {
  options.url = url + '/task/course/' + key
  options.method = 'GET'
  console.log('GET All Tasks from Course with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (body.error){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.getAllStudentsFromCourse = (key, callback) => {
  options.url = url + '/student/course/' + key
  options.method = 'GET'
  console.log('GET All Students from Course with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (body.error){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.getAllLecturersFromCourse = (key, callback) => {
  options.url = url + '/lecturer/course/' + key
  options.method = 'GET'
  console.log('GET All Lecturers from Course with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (body.error){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

//solution / submission
exports.validateSolution = (solution, callback) => {
  options.json = solution
  options.url = url + '/solution'
  options.method = 'POST'
  console.log('Post Solution with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.deleteSolution = (key, callback) => {
  options.url = url + '/solution/' + key
  options.method = 'DELETE'
  console.log('DELETE Solution with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

//solution / submission
exports.updateSolution = (solution, callback) => {
  options.json = solution
  options.url = url + '/solution/update'
  options.method = 'POST'
  console.log('Update Solution with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

exports.getAllSubmissionsForOneTask = (task_id, callback) => {
  options.url = url + '/submission/student/task/' + task_id
  options.method = 'GET'
  console.log('GET All Submissions for one Task with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (err){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}

//admin
exports.getAllUsers = (callback) => {
  options.url = url + '/users/'
  options.method = 'GET'
  console.log('GET All Users with options: ' + JSON.stringify(options));
  request(options, (err, res, body) => {
      if (body.error){
        console.log("There was an Error")
        body = null
      }
      callback(JSON.parse(body))
    })
}
