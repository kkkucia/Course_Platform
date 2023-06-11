import { Route, Routes, Navigate } from "react-router-dom";
import Navbar from "./Navbar";
import Home from "./Home";
import AvailableCoursesList from "./Pages/AvailableCoursesList";

import "../Styles/Main.css"
import AddCourses from "./Pages/AddCourse";
import GeneralList from "./Pages/GeneralList";
import OneCourseExtended from "./Utils/OneCourseExtended";
import GeneralProcedurePage from "./Pages/GeneralProcedurePage";
import GeneralFunctionPage from "./Pages/GeneralFunctionPage";


const Main = () => {

    return (
        <>
            <Navbar />
            <Routes>
                <Route index element={<Home />} />
                <Route path="/" element={<Home />} />
                <Route path="/basic/courses" element={<GeneralList srcLink={'http://localhost:8080/courses'} text={"List of all courses"} />} />
                <Route path="/basic/courses/:id" element={<OneCourseExtended></OneCourseExtended>} />
                <Route path="/basic/categories" element={<GeneralList srcLink={'http://localhost:8080/categories'} text={"List of all categories"} />} />
                <Route path="/basic/reservations" element={<GeneralList srcLink={'http://localhost:8080/reservations'} text={"List of all reservations"} />} />
                <Route path="/basic/participants" element={<GeneralList srcLink={'http://localhost:8080/participants'} text={"List of all participants"} />} />
                <Route path="/basic/mentors" element={<GeneralList srcLink={'http://localhost:8080/mentors'} text={"List of all mentors"} />} />
                <Route path="/basic/logs" element={<GeneralList srcLink={'http://localhost:8080/logs'} text={"List of all logs"} />} />

                <Route path="/views/availableCourses" element={<AvailableCoursesList />} />
                <Route path="/views/invoices" element={<GeneralList srcLink={'http://localhost:8080/invoices'} text={"List of invoices"} />} />
                <Route path="/views/cancelledReservations" element={<GeneralList srcLink={'http://localhost:8080/reservations/canceled'} text={"List of cancelled reservations"} />} />
                
                <Route path="/procedures/addCourse" element={<AddCourses />} />
                <Route path="/procedures/addMentorToCourse" element={<GeneralProcedurePage requiredData={["mentor_id", "course_id"]} inputTypes={["number", "number"]} text={"Add mentor to a course"} link={'http://localhost:8080/courses/mentors/add'}></GeneralProcedurePage>} />
                <Route path="/procedures/payForReservation" element={<GeneralProcedurePage requiredData={["reservation_id", "payment_type"]} inputTypes={["number", "text"]} text={"Pay for specific reservation"} link={'http://localhost:8080/payments/reservations'}></GeneralProcedurePage>} />
                <Route path="/procedures/payForParticipantReservations" element={<GeneralProcedurePage requiredData={["participant_id", "payment_type"]} inputTypes={["number", "text"]} text={"Pay for all participant reservations"} link={'http://localhost:8080/payments/participants/reservations'}></GeneralProcedurePage>} />
                <Route path="/procedures/makeReservation" element={<GeneralProcedurePage requiredData={["course_id", "participant_id"]} inputTypes={["number", "number"]} text={"Make a reservation"} link={'http://localhost:8080/reservations'}></GeneralProcedurePage>} />
                <Route path="/procedures/addCourseToCategory" element={<GeneralProcedurePage requiredData={["course_id", "category_id"]} inputTypes={["number", "number"]} text={"Add a category to a course"} link={'http://localhost:8080/categories/courses'}></GeneralProcedurePage>} />
                <Route path="/procedures/cancelReservation" element={<GeneralProcedurePage requiredData={["reservation_id"]} inputTypes={["number"]} text={"Cancel a reservation"} link={'http://localhost:8080/reservations/cancel'}></GeneralProcedurePage>} />
                
                <Route path="/functions/availableCoursesBetweenDates" element={<GeneralFunctionPage requiredData={["startDate", "endDate"]} inputTypes={["date", "date"]} text={"Search for available courses between two dates"} link={'http://localhost:8080/courses/available/between'}></GeneralFunctionPage>} />
                <Route path="/functions/availableCoursesBetweenDatesByCategory" element={<GeneralFunctionPage requiredData={["startDate", "endDate", "category_id"]} inputTypes={["date", "date", "number"]} text={"Search for available courses between two dates by category"} link={'http://localhost:8080/courses/categories/available/between'}></GeneralFunctionPage>} />
                <Route path="/functions/getReservationsFromCourses" element={<GeneralFunctionPage requiredData={["course_id"]} inputTypes={["number"]} text={"Get reservations from course"} link={'http://localhost:8080/reservations/courses'}></GeneralFunctionPage>} />
                <Route path="/functions/mentorsFromCourse" element={<GeneralFunctionPage requiredData={["course_id"]} inputTypes={["number"]} text={"Get mentors from course"} link={'http://localhost:8080/courses/mentors'}></GeneralFunctionPage>} />
                <Route path="/functions/participantsFromCourse" element={<GeneralFunctionPage requiredData={["course_id"]} inputTypes={["number"]} text={"Get participants from course"} link={'http://localhost:8080/courses/participants'}></GeneralFunctionPage>} />
                <Route path="/functions/invoicesFromParticipant" element={<GeneralFunctionPage requiredData={["participant_id"]} inputTypes={["number"]} text={"Get invoices from participant"} link={'http://localhost:8080/invoices/users'}></GeneralFunctionPage>} />
                <Route path="/functions/participantUnpaidSum" element={<GeneralFunctionPage requiredData={["participant_id"]} inputTypes={["number"]} text={"Get amount to pay for participant"} link={'http://localhost:8080/invoices/unpaid/sum'}></GeneralFunctionPage>} />
                <Route path="/functions/participantUnpaidReservations" element={<GeneralFunctionPage requiredData={["user_id"]} inputTypes={["number"]} text={"Get unpaid reservations for user"} link={'http://localhost:8080/reservations/unpaid/users'}></GeneralFunctionPage>} />
                <Route path="/functions/participantReservations" element={<GeneralFunctionPage requiredData={["participant_id"]} inputTypes={["number"]} text={"Get reservations for participant"} link={'http://localhost:8080/reservations/participants'}></GeneralFunctionPage>} />

                <Route path="*" element={<Navigate to="/" />} />
            </Routes>
        </>
    );
};

export default Main;