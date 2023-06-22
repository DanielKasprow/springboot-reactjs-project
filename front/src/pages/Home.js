import React, { useRef, useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from "react-router-dom";
import "../../src/css/Styles.css";

var sortDir = "asc";

export default function Home() {
    const inputRef = useRef(null);
    const [products, setProducts] = useState([]);

    useEffect(() => {
        loadProducts();
    }, []);


    const loadProducts = async () => {
        const result = await axios.get("http://localhost:8080/products?sort=name," + sortDir);
        setProducts(result.data);
    };
    const searchProducts = async () => {
        if (inputRef.current.value !== "") {
            const result = await axios.get("http://localhost:8080/products/search/" + inputRef.current.value);
            setProducts(result.data);
        }
    };
    const deleteProduct = async (id) => {
        await axios.delete(`http://localhost:8080/product/${id}`);
        loadProducts();
    };


    const sortData = async () => {
        sortDir === "asc" ? sortDir = "desc" : sortDir = "asc";
        loadProducts();

    };

    return (
        <div className='container'>

            <div style={{ float: "right" }}>
                <input
                    ref={inputRef}
                    type={"text"}
                    className="form-control"
                    placeholder="Search"
                    name="search"
                    style={{ float: "left" , width:"70%" }}
                />
                <button
                    onClick={searchProducts}
                    type="button"
                    className='btn btn-outline-primary mx-2'
                    style={{ float: "right" }}
                >
                    search
                </button>
            </div>
            <div className='py-4'>
                <table className="table border shadow">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th onClick={sortData} type="button">Name<div
                                className={
                                    sortDir === "desc"
                                        ? "arrow arrow-up"
                                        : "arrow arrow-down"
                                }
                            >
                            </div></th>
                            <th scope="col">Description</th>
                            <th scope="col">Price</th>
                            <th scope="col">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {products.map((product, index) => (
                            <tr>
                                <th scope="row" key={index}>{index + 1}</th>
                                <td>{product.name}</td>
                                <td>{product.description}</td>
                                <td>{product.price}</td>
                                <td>
                                    <Link
                                        className="btn btn-primary mx-2"
                                        to={`/viewProduct/${product.id}`}
                                    >
                                        View
                                    </Link>
                                    <Link
                                        className="btn btn-outline-primary mx-2"
                                        to={`/editProduct/${product.id}`}
                                    >
                                        Edit
                                    </Link>
                                    <button
                                        className="btn btn-danger mx-2"
                                        onClick={() => deleteProduct(product.id)}
                                    >
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}


                    </tbody>
                </table>
            </div>
        </div>
    )
}
