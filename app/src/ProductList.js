import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

const ProductList = () => {

    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);

        fetch('product')
            .then(response => response.json())
            .then(data => {
                setProducts(data);
                setLoading(false);
            })
    }, []);

    if (loading) {
        return <p>Loading...</p>;
    }

    const productList = products.map(product => {
        return <tr key={product.productId}>
            <td style={{whiteSpace: 'nowrap'}}>{product.sku}</td>
            <td style={{whiteSpace: 'nowrap'}}>{product.name}</td>
            <td style={{whiteSpace: 'nowrap'}}>{product.description}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={"/product"}>Edit</Button>
                </ButtonGroup>
            </td>
        </tr>
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <div className="float-end">
                    <Button color="success" tag={Link} to="/product">Add Product</Button>
                </div>
                <h3>Products</h3>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="25%">SKU</th>
                        <th width="25%">Name</th>
                        <th width="25%">Description</th>
                    </tr>
                    </thead>
                    <tbody>
                    {productList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
};

export default ProductList;