import React, {useState} from 'react'
import axios from 'axios'
import '../../Styles/Form.css'

const AddCourses = () => {
  const [responseMessage, setResponseMessage] = useState("")
  const addCourse = (event) => {
    event.preventDefault()
    let course = {
      title:event.target.courseTitle.value,
      price:event.target.coursePrice.value,
      startDate:event.target.courseStartDate.value,
      endDate:event.target.courseEndDate.value,
      maxNoPlaces:event.target.courseMaxPlaces.value,
      availablePlaces:event.target.courseAvailablePlaces.value,
    }
    // console.log(course)
    axios.post('http://localhost:8080/courses', course, {headers:{"Content-Type":"application/json"}}).then((res) => {
      if (res.status === 200) {
        setResponseMessage("Added new "+ course.title +" course")
      }
    })
    .catch((err) => {
      console.log(err.response.data)
      setResponseMessage("Error with adding "+ course.title +" course. Error:" + err.response.data.error)
    })
    
  }
  return (
    <div>
      <h2>Add a course:</h2>
      <form onSubmit={addCourse}>
        <label>
          Course title:
          <input type="text" name="courseTitle" placeholder="Course title"></input>
        </label>
        <label>
          Course price:
          <input type="number" name='coursePrice' step="0.01" placeholder='Course price'></input>
        </label>
        <label>
          Course start date
          <input type='date' name='courseStartDate'></input>
        </label>
        <label>
          Course end date:
          <input type='date' name='courseEndDate'></input>
        </label>
        <label>
          Max course places:
          <input type='number' name='courseMaxPlaces' placeholder='Max course places'></input>
        </label>
        <label>
          Available course places:
          <input type='number' name='courseAvailablePlaces' placeholder='Available course places'></input>
        </label>
        <input type='submit' name='submit'></input>
      </form>
      <div id='response'>{responseMessage}</div>
    </div>
  )
}

export default AddCourses;