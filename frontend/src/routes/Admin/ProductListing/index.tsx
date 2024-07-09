/* eslint-disable react-hooks/exhaustive-deps */
import './styles.css';
import editIcon from '../../../assets/edit.svg';
import deleteIcon from '../../../assets/delete.svg';
import { useEffect, useState } from 'react';
import * as productService from '../../../services/product-service';
import { ProductDTO } from '../../../models/product';
import SearchBar from '../../../components/SearchBar';
import ButtonNextPage from '../../../components/ButtonNextPage';
import DialogInfo from '../../../components/DialogInfo';
import DialogConfirmation from '../../../components/DialogConfirmation';

type QuaryParams = {
    page: number;
    name: string;
}

export default function ProductListing() {

    const [dialogInfoData, setDialogInfoData] = useState({
        visible: false,
        message: "Operação com Sucesso!"
    });

    const [dialogConfirmationData, setDialogConfirmationData] = useState({
        visible: false,
        message: "Tem certeza?"
    });


    const [isLastPage, setIsLastPage] = useState(false);

    const [products, setProducts] = useState<ProductDTO[]>([]);

    const [quaryParams, setQuaryParams] = useState<QuaryParams>({
        page: 0,
        name: ""
    });


    useEffect(() => {
        productService.findPageResquest(quaryParams.page, quaryParams.name)
            .then(response => {
                const nextPage = response.data.content;
                setProducts(products.concat(nextPage));
                setIsLastPage(response.data.last)
            });
    }, [quaryParams]);

    function handleSearch(searchText: string) {
        setProducts([]);
        setQuaryParams({ ...quaryParams, page: 0, name: searchText });
    }

    function handleNextPageClick() {
        setQuaryParams({ ...quaryParams, page: quaryParams.page + 1 });
    }

    function handleDialogInfoClose() {
        setDialogConfirmationData({ ...dialogInfoData, visible: false });
    }

    function handleDeleteClick() {
        setDialogConfirmationData({ ...dialogConfirmationData, visible: true });
    }

    function handleDialogConfirmationAnswer(answer: boolean) {
        setDialogConfirmationData({...dialogConfirmationData, visible: false})
    }

    return (
        <main>
            <section id="product-listing-section" className="dsc-container">
                <h2 className="dsc-section-title dsc-mb20">Cadastro de produtos</h2>

                <div className="dsc-btn-page-container dsc-mb20">
                    <div className="dsc-btn dsc-btn-white">Novo</div>
                </div>

                <SearchBar onSearch={handleSearch} />

                <table className="dsc-table dsc-mb20 dsc-mt20">
                    <thead>
                        <tr>
                            <th className="dsc-tb576">ID</th>
                            <th></th>
                            <th className="dsc-tb768">Preço</th>
                            <th className="dsc-txt-left">Nome</th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            products.map(product => (

                                <tr key={product.id}>
                                    <td className="dsc-tb576">{product.id}</td>
                                    <td><img className="dsc-product-listing-image" src={product.imgUrl} alt={product.name} /></td>
                                    <td className="dsc-tb768">{product.price}</td>
                                    <td className="dsc-txt-left">{product.name}</td>
                                    <td><img className="dsc-product-listing-btn" src={editIcon} alt="Editar" /></td>
                                    <td><img onClick={handleDeleteClick} className="dsc-product-listing-btn" src={deleteIcon} alt="Deletar" /></td>
                                </tr>

                            ))
                        }
                    </tbody>
                </table>

                {
                    !isLastPage &&
                    <div onClick={handleNextPageClick}>
                        <ButtonNextPage />
                    </div>

                }
            </section>
            {
                dialogInfoData.visible &&
                <DialogInfo message={dialogInfoData.message}
                    onDialogClose={handleDialogInfoClose}
                />
            }

            {
                dialogConfirmationData.visible &&
                <DialogConfirmation message={dialogConfirmationData.message}
                    onDialogAnswer={handleDialogConfirmationAnswer}
                />
            }

        </main>
    )
}