import React from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import CustomerList from './CustomerList';
import ProductList from './ProductList';

const App = () => {
    return (
        <Router>
            <Routes>
                <Route exact path="/" element={<Home/>}/>
                <Route path='/customers' element={<CustomerList/>}/>
                <Route path='/products' element={<ProductList/>}/>
            </Routes>
        </Router>
    )
}

export default App;