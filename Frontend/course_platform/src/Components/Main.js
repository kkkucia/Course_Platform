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

                <Route path="/views/availableCourses" element={<AvailableCoursesList />} />
                <Route path="/views/invoices" element={<GeneralList srcLink={'http://localhost:8080/invoices'} text={"List of invoices"} />} />
                <Route path="/views/cancelledReservations" element={<GeneralList srcLink={'http://localhost:8080/reservations/canceled'} text={"List of cancelled reservations"} />} />
                
                <Route path="/procedures/addCourse" element={<AddCourses />} />
                <Route path="/procedures/addMentorToCourse" element={<GeneralProcedurePage requiredData={["mentor_id", "course_id"]} inputTypes={["number", "number"]} text={"Add mentor to a course"} link={'http://localhost:8080/courses/mentors'}></GeneralProcedurePage>} />
                <Route path="/procedures/payForReservation" element={<GeneralProcedurePage requiredData={["reservation_id", "payment_type"]} inputTypes={["number", "text"]} text={"Pay for specific reservation"} link={'http://localhost:8080/payments/reservations'}></GeneralProcedurePage>} />
                <Route path="/procedures/payForParticipantReservations" element={<GeneralProcedurePage requiredData={["participant_id", "payment_type"]} inputTypes={["number", "text"]} text={"Pay for all participant reservations"} link={'http://localhost:8080/payments/participants/reservations'}></GeneralProcedurePage>} />
                <Route path="/procedures/makeReservation" element={<GeneralProcedurePage requiredData={["course_id", "participant_id"]} inputTypes={["number", "number"]} text={"Make a reservation"} link={'http://localhost:8080/reservations'}></GeneralProcedurePage>} />
                
                <Route path="/functions/availableCoursesBetweenDates" element={<GeneralFunctionPage requiredData={["startDate", "endDate"]} inputTypes={["date", "date"]} text={"Search for available courses between two dates"} link={'http://localhost:8080/courses/available/between'}></GeneralFunctionPage>} />
                <Route path="/functions/availableCoursesBetweenDatesByCategory" element={<GeneralFunctionPage requiredData={["startDate", "endDate", "category_id"]} inputTypes={["date", "date", "number"]} text={"Search for available courses between two dates by category"} link={'http://localhost:8080/courses/categories/available/between'}></GeneralFunctionPage>} />
                <Route path="/functions/getReservationsFromCourses" element={<GeneralFunctionPage requiredData={["course_id"]} inputTypes={["number"]} text={"Get reservations from course"} link={'http://localhost:8080/reservations/courses'}></GeneralFunctionPage>} />

                <Route path="*" element={<Navigate to="/" />} />
            </Routes>
        </>
    );
};

export default Main;