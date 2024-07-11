/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable @typescript-eslint/no-explicit-any */

export default function FormInput(props: any) {

    const {
        validation,
        invalid = "false",
        dirty = "false",
        onTrunDirty,
        ...inputProps } = props;

    function handleBlur() {
        onTrunDirty(props.name);
    }

    return (
        <input
            {...inputProps}
            onBlur={handleBlur}
            data-invalid={invalid}
            data-dirty={dirty}
        />
    );
}