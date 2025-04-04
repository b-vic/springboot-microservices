import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import { useCookies } from 'react-cookie';

const ProductList = () => {

    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(false);
    const [cookies] = useCookies(['XSRF-TOKEN']);

    useEffect(() => {
        setLoading(true);

        fetch('product')
            .then(response => response.json())
            .then(data => {
                setProducts(data);
                setLoading(false);
            })
    }, []);

    const remove = async (id) => {
        await fetch(`/product/${id}`, {
            method: 'DELETE',
            headers: {
                'X-XSRF-TOKEN': cookies['XSRF-TOKEN'],
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        }).then(() => {
            let updatedProducts = [...products].filter(i => i.sku !== id);
            setProducts(updatedProducts);
        });
    }

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
                    <Button size="sm" color="primary" tag={Link} to={"/products/" + product.sku}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => remove(product.sku)}>Delete</Button>
                </ButtonGroup>
            </td>
        </tr>
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <div className="float-end">
                    <Button color="success" tag={Link} to="/products/new">Add Product</Button>
                </div>
                <h3>Products</h3>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="33%">SKU</th>
                        <th width="33%">Name</th>
                        <th width="33%">Description</th>
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