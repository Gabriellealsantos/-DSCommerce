/* eslint-disable @typescript-eslint/no-explicit-any */

export const selectStyles = {
    control: (provided: any) => ({
        ...provided,
        minHeight: "40px",
        border: "none",
        boxShadow: "none",
        "&:hover": {
            border: "none",
        },
    }),
    placeholder: (provide: any) => ({
        ...provide,
        color: "var(--dsc-color-font-placeholder)",
    }),
    indicatorSeparator: (provide: any) => ({
        ...provide,
        display: "none",
    }),
}