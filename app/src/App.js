import React from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import CustomerList from './CustomerList';
import CustomerEdit from './CustomerEdit';
import ProductList from './ProductList';
import ProductEdit from './ProductEdit';

const App = () => {
    return (
        <Router>
            <Routes>
                <Route exact path="/" element={<Home/>}/>
                <Route path='/customers' exact={true} element={<CustomerList/>}/>
                <Route path="/customers/:id" exact={true} element={<CustomerEdit/>}/>
                <Route path='/products' exact={true} element={<ProductList/>}/>
                <Route path='/products/:id' exact={true} element={<ProductEdit/>}/>
            </Routes>
        </Router>
    )
}

export default App;