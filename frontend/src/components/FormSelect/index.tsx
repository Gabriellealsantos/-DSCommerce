/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable @typescript-eslint/no-explicit-any */

import Select from "react-select";

export default function FormSelect(props: any) {

    const {
        className,
        validation,
        invalid = "false",
        dirty = "false",
        onTrunDirty,
        ...selectProps } = props;

    function handleBlur() {
        onTrunDirty(props.name);
    }

    return (
        <div
            className={className}
            data-invalid={invalid}
            data-dirty={dirty}
        >
            <Select
                {...selectProps}
                onBlur={handleBlur}
            />
        </div>

    );
}