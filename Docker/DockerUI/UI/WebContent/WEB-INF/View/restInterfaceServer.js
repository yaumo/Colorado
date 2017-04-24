import express from 'express'
import request from 'request'

// create Router
const router = express.Router();
/*const url = 'http://backend:8080'
let options = {rejectUnauthorized: false}
let sess

router.get('/', (req, res) => {
	res.json({ message: 'Welcome to our api!' });
});

router.route('/login').get((clientRequest, clientResponse) => {
sess = clientRequest.session

		   options.url = url;
      options.method = 'GET'
      console.log('Sending Student with options: ' + JSON.stringify(options))
      request(options, (error, response, body) => {
					body = errorHandler(error, body, "GET a Student")
					console.log('Sending to Client: ' + JSON.stringify(body))
          clientResponse.json(body)
        })
			}
)


const errorHandler = (error, body, code) => {
	let logObject = {}
	logObject.errorAt = code
	if (error){
		logObject.code = error.code
		console.log(logObject)
		return  logObject
	}
	else {
		try{
			body = JSON.parse(body)
			return body
		}
		catch(e){
			logObject.code = 'noJSON'
			return logObject
		}
	}
}

//TODO: Proxy Handler to minimize Code !?
//const proxyHandler

//STUDENT

//Get Student
router.route('/student/:student_id')
  .get((clientRequest, clientResponse) => {

		sess = clientRequest.session

		if (sess.username){
			let studentKey

			if(sess.isStudent){
				studentKey = sess.username
			}
			else{
				studentKey = clientRequest.params.student_id
			}

      options.url = url + '/student/' + studentKey
      options.method = 'GET'
      console.log('Sending Student with options: ' + JSON.stringify(options))
      request(options, (error, response, body) => {
					body = errorHandler(error, body, "GET a Student")
					console.log('Sending to Client: ' + JSON.stringify(body))
          clientResponse.json(body)
        })
			}
			else{
				clientResponse.sendStatus(403)
			}
    })

//Delete Student
 .delete((clientRequest, clientResponse) =>{
	 sess = clientRequest.session
 		if(sess.username && sess.isAdmin){
 		  options.url = url + '/student/' + clientRequest.params.student_id
 		  options.method = 'DELETE'
       console.log('DELETE Student with options: ' + JSON.stringify(options))
       request(options, (error, response, body) => {
 				body = errorHandler(error, body, "DELETE a Student")
 				console.log('Sending to Client: ' + JSON.stringify(body));
         clientResponse.json(body)
       })
 		}
 		else{
 			clientResponse.sendStatus(403)
 		}
 })

// Post / Create Student
router.route('/student')
.post((clientRequest, clientResponse) => {
	sess = clientRequest.session
		if(sess.username && !sess.isStudent){
			options.json = clientRequest.body
		  options.url = url + '/student'
		  options.method = 'POST'
      console.log('Sending Student with options: ' + JSON.stringify(options))
      request(options, (error, response, body) => {
				body = errorHandler(error, body, "POST a Student")
				console.log('Sending to Client: ' + JSON.stringify(body));
        clientResponse.json(body)
      })
		}
		else{
			clientResponse.sendStatus(403)
		}
})

//Update Student
router.route('/student/update')
.post((clientRequest, clientResponse) => {
	sess = clientRequest.session
		if(sess.isAdmin || !sess.isStudent){
			options.json = clientRequest.body
		  options.url = url + '/lecturer/update'
		  options.method = 'POST'
      console.log('Update Lecturer with options: ' + JSON.stringify(options))
      request(options, (error, response, body) => {
				body = errorHandler(error, body, "Update a Lecturer")
				console.log('Sending to Client: ' + JSON.stringify(body));
        clientResponse.json(body)
      })
		}
		else{
			clientResponse.sendStatus(403)
		}
})

// LECTURER

router.route('/lecturer/:lecturer_id')
  .get((clientRequest, clientResponse) => {
		sess = clientRequest.session
		if (sess.username && !sess.isStudent){
			options.url = url + '/lecturer/' + sess.username
      options.method = 'GET'
      console.log('Sending Lecturer with options: ' + JSON.stringify(options))
      request(options, (error, response, body) => {
				body = errorHandler(error, body, "GET a Lecturer")
				console.log('Sending to Client: ' + JSON.stringify(body));
        clientResponse.json(body)
      })
		}
	else{
		clientResponse.sendStatus(403)
  }
})

//Delete Lecturer
 .delete((clientRequest, clientResponse) =>{
	 sess = clientRequest.session
 		if(sess.username && sess.isAdmin){
 		  options.url = url + '/lecturer/' + clientRequest.params.lecturer_id
 		  options.method = 'DELETE'
       console.log('DELETE Lecturer with options: ' + JSON.stringify(options))
       request(options, (error, response, body) => {
 				body = errorHandler(error, body, "DELETE a Lecturer")
 				console.log('Sending to Client: ' + JSON.stringify(body));
         clientResponse.json(body)
       })
 		}
 		else{
 			clientResponse.sendStatus(403)
 		}
 })

//Delete Lecturer from course
router.route('/lecturer/:lecturer_id/course/course_id')
 .delete((clientRequest, clientResponse) =>{
	 sess = clientRequest.session
 		if(sess.username && !sess.isStudent){
 		  options.url = url + '/lecturer/' + clientRequest.params.lecturer_id + 'course'
			+ clientRequest.params.course_id
 		  options.method = 'DELETE'
       console.log('DELETE Lecturer from Course with options: ' + JSON.stringify(options))
       request(options, (error, response, body) => {
 				body = errorHandler(error, body, "DELETE a Lecturer from Course")
 				console.log('Sending to Client: ' + JSON.stringify(body));
         clientResponse.json(body)
       })
 		}
 		else{
 			clientResponse.sendStatus(403)
 		}
 })

//Get all lecturer
router.route('/lecturer')
	.get((clientRequest, clientResponse) => {
		sess = clientRequest.session
		if (sess.username && !sess.isStudent){
			options.url = url + '/lecturer/'
			options.method = 'GET'
			console.log('Sending Lecturer with options: ' + JSON.stringify(options))
			request(options, (error, response, body) => {
				body = errorHandler(error, body, "GET all Lecturer")
				console.log('Sending to Client: ' + JSON.stringify(body));
				clientResponse.json(body)
			})
		}
		else{
			clientResponse.sendStatus(403)
		}
	})

//Post / Create Lecturer
.post((clientRequest, clientResponse) => {
	sess = clientRequest.session
		if(sess.isAdmin){
			options.json = clientRequest.body
		  options.url = url + '/lecturer'
		  options.method = 'POST'
      console.log('POST Lecturer with options: ' + JSON.stringify(options))
      request(options, (error, response, body) => {
				body = errorHandler(error, body, "POST a Lecturer")
				console.log('Sending to Client: ' + JSON.stringify(body));
        clientResponse.json(body)
      })
		}
		else{
			clientResponse.sendStatus(403)
		}
})

//Update Lecturer
router.route('/lecturer/update')
.post((clientRequest, clientResponse) => {
	sess = clientRequest.session
		if(sess.isAdmin || !sess.isStudent){
			options.json = clientRequest.body
		  options.url = url + '/lecturer/update'
		  options.method = 'POST'
      console.log('Update Lecturer with options: ' + JSON.stringify(options))
      request(options, (error, response, body) => {
				body = errorHandler(error, body, "Update a Lecturer")
				console.log('Sending to Client: ' + JSON.stringify(body));
        clientResponse.json(body)
      })
		}
		else{
			clientResponse.sendStatus(403)
		}
})

//Get all tasks from Lecturer
router.route('/task/lecturer/')
	  .get((clientRequest, clientResponse) => {
			sess = clientRequest.session
			console.log(sess.username);
			if(sess.username && !sess.isStudent){
	      options.url = url + '/task/lecturer/' + sess.username
	      options.method = 'GET'
	      console.log('Getting Lecturer with options: ' + JSON.stringify(options))
	      request(options, (error, response, body) => {
					body = errorHandler(error, body, "GET all Tasks from Lecturer")
					console.log('Sending to Client: ' + JSON.stringify(body));
          clientResponse.json(body)
        })
			}
			else{
				clientResponse.sendStatus(403)
			}
    })

//Get all Courses from Lecturer
router.route('/course/lecturer')
	.get((clientRequest, clientResponse) => {
		sess = clientRequest.session
		console.log(sess.username);
		if(sess.username && !sess.isStudent){
			options.url = url + '/course/lecturer/' + sess.username
			options.method = 'GET'
			console.log('Getting Lecturer with options: ' + JSON.stringify(options))
			request(options, (error, response, body) => {
				body = errorHandler(error, body, "GET all Courses from Lecturer")
				console.log('Sending to Client: ' + JSON.stringify(body));
				clientResponse.json(body)
			})
		}
		else{
			clientResponse.sendStatus(403)
		}
	})

	//Add Lecturer to Course
	router.route('/lecturer/:excerciseName/course/:courseName')
		.post((clientRequest, clientResponse) => {
			sess = clientRequest.session
				if(sess.isAdmin){
					options.json = clientRequest.body
					options.url = url + '/lecturer/'+ clientRequest.params.excerciseName +'/course'
					+ clientRequest.params.courseName
					options.method = 'POST'
					console.log('POST / Add Lecturer to Course options: ' + JSON.stringify(options))
					request(options, (error, response, body) => {
						body = errorHandler(error, body, "Add Lecturer to Course")
						console.log('Sending to Client: ' + JSON.stringify(body));
						clientResponse.json(body)
					})
				}
				else{
					clientResponse.sendStatus(403)
				}
		})


// TASKS

//Get Task
router.route('/task/:task_id')
  .get((clientRequest, clientResponse) => {
		sess = clientRequest.session
		if(sess.username && !sess.isStudent){
      options.url = url + '/task/' + clientRequest.params.task_id
      options.method = 'GET'
      console.log('Sending Excercise with options: ' + JSON.stringify(options))
      request(options, (error, response, body) => {
				body = errorHandler(error, body, "GET an Excercise")
				if(body.errorAt){
					clientResponse.json(body)
					console.log('Sending to Client: ' + JSON.stringify(body))
				}else{
					let responseObject = {}
					responseObject.excercisename = body.excercisename
					responseObject.excercisedescription = body.excercisedescription
					console.log('Sending to Client: ' + JSON.stringify(responseObject))
					clientResponse.json(responseObject)
				}
      })
		}
		else{
			clientResponse.sendStatus(403)
		}
  })

//Delete Task from Lecturer
router.route('/task/:task_id/lecturer/:lecturer_id')
	 .delete((clientRequest, clientResponse) =>{
		 sess = clientRequest.session
	 		if(sess.username && !sess.isStudent){
	 		  options.url = url + '/task/' + clientRequest.params.task_id + '/lecturer/' +
				clientRequest.params.lecturer_id
	 		  options.method = 'DELETE'
	       console.log('DELETE Task from Lecturer with options: ' + JSON.stringify(options))
	       request(options, (error, response, body) => {
	 				body = errorHandler(error, body, "DELETE a Task from Lecturer")
	 				console.log('Sending to Client: ' + JSON.stringify(body));
	         clientResponse.json(body)
	       })
	 		}
	 		else{
	 			clientResponse.sendStatus(403)
	 		}
	 })

//Delete Task from Course
router.route('/task/:task_id/course/:course_id')
	 	 .delete((clientRequest, clientResponse) =>{
	 		 sess = clientRequest.session
	 	 		if(sess.username && !sess.isStudent){
	 	 		  options.url = url + '/task/' + clientRequest.params.task_id + '/lecturer/' +
	 				clientRequest.params.course_id
	 	 		  options.method = 'DELETE'
	 	       console.log('DELETE Task from Course with options: ' + JSON.stringify(options))
	 	       request(options, (error, response, body) => {
	 	 				body = errorHandler(error, body, "DELETE a Task from Course")
	 	 				console.log('Sending to Client: ' + JSON.stringify(body));
	 	         clientResponse.json(body)
	 	       })
	 	 		}
	 	 		else{
	 	 			clientResponse.sendStatus(403)
	 	 		}
	 	 })

//Post / Create Task
router.route('/task')
.post((clientRequest, clientResponse) => {
		sess = clientRequest.session
			if(sess.isAdmin || !sess.isStudent){
				options.json = clientRequest.body
			  options.url = url + '/task'
			  options.method = 'POST'
	      console.log('POST Task with options: ' + JSON.stringify(options))
	      request(options, (error, response, body) => {
					body = errorHandler(error, body, "POST a Task")
					console.log('Sending to Client: ' + JSON.stringify(body));
	        clientResponse.json(body)
	      })
			}
			else{
				clientResponse.sendStatus(403)
			}
	})

//Update Task
router.route('/task/update')
	.post((clientRequest, clientResponse) => {
		sess = clientRequest.session
			if(sess.isAdmin || !sess.isStudent){
				options.json = clientRequest.body
			  options.url = url + '/task/update'
			  options.method = 'POST'
	      console.log('Update Lecturer with options: ' + JSON.stringify(options))
	      request(options, (error, response, body) => {
					body = errorHandler(error, body, "Update a Task")
					console.log('Sending to Client: ' + JSON.stringify(body));
	        clientResponse.json(body)
	      })
			}
			else{
				clientResponse.sendStatus(403)
			}
	})

//Add Task to Course
router.route('/task/:excerciseName/course/:courseName')
	.post((clientRequest, clientResponse) => {
		sess = clientRequest.session
			if(sess.isAdmin){
				options.json = clientRequest.body
				options.url = url + '/task/'+ clientRequest.params.excerciseName +'/course'
				+ clientRequest.params.courseName
				options.method = 'POST'
				console.log('POST / Add Task to Course options: ' + JSON.stringify(options))
				request(options, (error, response, body) => {
					body = errorHandler(error, body, "Add Task to Course")
					console.log('Sending to Client: ' + JSON.stringify(body));
					clientResponse.json(body)
				})
			}
			else{
				clientResponse.sendStatus(403)
			}
	})

// COURSE

router.route('/course/:course_id')
  .get((clientRequest, clientResponse) => {
		sess = clientRequest.session
		if (sess.username){
			options.url = url + '/course/' + clientRequest.params.course_id
      options.method = 'GET'
      console.log('GET Course with options: ' + JSON.stringify(options))
      request(options, (error, response, body) => {
				body = errorHandler(error, body, "GET a Course")
				console.log('Sending to Client: ' + JSON.stringify(body));
        clientResponse.json(body)
      })
		}
	else{
    clientResponse.sendStatus(403)
  }
})

//Delete Course
 .delete((clientRequest, clientResponse) =>{
	 sess = clientRequest.session
		if(sess.username && sess.isAdmin){
			options.url = url + '/course/' + clientRequest.params.course_id
			options.method = 'DELETE'
			 console.log('DELETE Course with options: ' + JSON.stringify(options))
			 request(options, (error, response, body) => {
				body = errorHandler(error, body, "DELETE a Course")
				console.log('Sending to Client: ' + JSON.stringify(body));
				 clientResponse.json(body)
			 })
		}
		else{
			clientResponse.sendStatus(403)
		}
 })

//Posting / Creating a Course
router.route('/course')
.post((clientRequest, clientResponse) => {
	sess = clientRequest.session
		if(sess.isAdmin){
			options.json = clientRequest.body
		  options.url = url + '/course'
		  options.method = 'POST'
      console.log('POST Course with options: ' + JSON.stringify(options))
      request(options, (error, response, body) => {
				body = errorHandler(error, body, "POST a Course")
				console.log('Sending to Client: ' + JSON.stringify(body));
        clientResponse.json(body)
      })
		}
		else{
			clientResponse.sendStatus(403)
		}
})

//Update course
router.route('/course/update')
.post((clientRequest, clientResponse) => {
	sess = clientRequest.session
		if(sess.isAdmin){
			options.json = clientRequest.body
		  options.url = url + '/course/update'
		  options.method = 'POST'
      console.log('Update Course with options: ' + JSON.stringify(options))
      request(options, (error, response, body) => {
				body = errorHandler(error, body, "Update a Course")
				console.log('Sending to Client: ' + JSON.stringify(body));
        clientResponse.json(body)
      })
		}
		else{
			clientResponse.sendStatus(403)
		}
})

//Get all Tasks From Course
router.route('/task/course/course_id')
  .get((clientRequest, clientResponse) => {
		sess = clientRequest.session
		if (sess.username){
			options.url = url + 'task/course/' + clientRequest.params.course_id
      options.method = 'GET'
      console.log('GET All Tasks from Course with options: ' + JSON.stringify(options))
      request(options, (error, response, body) => {
				body = errorHandler(error, body, "GET all Tasks from Course")
				console.log('Sending to Client: ' + JSON.stringify(body));
        clientResponse.json(body)
      })
		}
	else{
    clientResponse.sendStatus(403)
  }
})

//Get all Students From Course
router.route('/student/course/course_id')
  .get((clientRequest, clientResponse) => {
		sess = clientRequest.session
		if (sess.username && !sess.isStudent){
			options.url = url + 'student/course/' + clientRequest.params.course_id
      options.method = 'GET'
      console.log('GET All Students from Course with options: ' + JSON.stringify(options))
      request(options, (error, response, body) => {
				body = errorHandler(error, body, "GET all Students from Course")
				console.log('Sending to Client: ' + JSON.stringify(body));
        clientResponse.json(body)
      })
		}
		else{
			clientResponse.sendStatus(403)
  	}
	})

//Get all Lecturer From Course
router.route('/lecturer/course/course_id')
  .get((clientRequest, clientResponse) => {
		sess = clientRequest.session
		if (sess.username){
			options.url = url + 'student/course/' + clientRequest.params.course_id
      options.method = 'GET'
      console.log('GET All Lecturers from Course with options: ' + JSON.stringify(options))
      request(options, (error, response, body) => {
				body = errorHandler(error, body, "GET all Lecturer from Course")
				console.log('Sending to Client: ' + JSON.stringify(body));
        clientResponse.json(body)
      })
		}
		else{
			clientResponse.sendStatus(403)
	  }
	})

//get All Submissions For One Task
router.route('/submission/student/task/:task_id')
  .get((clientRequest, clientResponse) => {
		sess = clientRequest.session
		if (sess.username && sess.isStudent){
			options.url = url + 'submission/student/' + sess.username +
			'task' + clientRequest.params.task_id
      options.method = 'GET'
      console.log('Sending Solution with options: ' + JSON.stringify(options))
      request(options, (error, response, body) => {
				body = errorHandler(error, body, "GET a Submission")
				console.log('Sending to Client: ' + JSON.stringify(body));
        clientResponse.json(body)
      })
		}
		else{
			clientResponse.sendStatus(403)
    }
	})

//Check Solution
router.route('/solution')
	.post((clientRequest, clientResponse) => {
		sess = clientRequest.session
			if(sess.username && sess.isStudent){
				let postObject = clientRequest.body
				postObject.studentusername = sess.username
				options.json = postObject
				options.url = url + '/solution'
				options.method = 'POST'
				console.log('POST Student with options: ' + JSON.stringify(options))
				request(options, (error, response, body) => {
					body = errorHandler(error, body, "POST a Solution")
					console.log('Sending to Client: ' + JSON.stringify(body));
					clientResponse.json(body)
				})
			}
			else{
				clientResponse.sendStatus(403)
			}
	})

	//Update Solution
	router.route('/solution/update')
		.post((clientRequest, clientResponse) => {
			sess = clientRequest.session
				if(sess.username && sess.isStudent){
					let postObject = clientRequest.body
					postObject.studentusername = sess.username
					options.json = postObject
					options.url = url + '/solution/update'
					options.method = 'POST'
					console.log('Update Student with options: ' + JSON.stringify(options))
					request(options, (error, response, body) => {
						body = errorHandler(error, body, "Update a Solution")
						console.log('Sending to Client: ' + JSON.stringify(body));
						clientResponse.json(body)
					})
				}
				else{
					clientResponse.sendStatus(403)
				}
		})

//Delete Solution
router.route('/solution/:solution_id')
 .delete((clientRequest, clientResponse) =>{
	 sess = clientRequest.session
		if(sess.username && sess.isStudent){
			options.url = url + '/solution/' + clientRequest.params.student_id + '/student/' + sess.username
			options.method = 'DELETE'
			 console.log('DELETE Task with options: ' + JSON.stringify(options))
			 request(options, (error, response, body) => {
				body = errorHandler(error, body, "DELETE a Task")
				console.log('Sending to Client: ' + JSON.stringify(body));
				 clientResponse.json(body)
			 })
		}
		else{
			clientResponse.sendStatus(403)
		}
 })

//get All Users
router.route('/users')
  .get((clientRequest, clientResponse) => {
		sess = clientRequest.session
		if (sess.isAdmin){
			options.url = url + 'users'
      options.method = 'GET'
      console.log('GET All Lecturers from Course with options: ' + JSON.stringify(options))
      request(options, (error, response, body) => {
				body = errorHandler(error, body, "GET all Users")
				console.log('Sending to Client: ' + JSON.stringify(body));
        clientResponse.json(body)
      })
		}
		else{
			clientResponse.sendStatus(403)
    }
	}) 

//TODO: ChangePassword
*/
export default router 
