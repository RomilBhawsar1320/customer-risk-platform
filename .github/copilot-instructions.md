# Customer Risk Platform Repository Instructions

## Project purpose

This repository implements a metadata-driven customer risk assessment and
credit-offer platform using Java, Apache Spark, Maven, JSON configuration,
synthetic relational CSV data, and Customer360 processing.

The platform:

1. Generates related synthetic datasets using a common customer_id.
2. Reads dataset definitions from pipeline-config.json.
3. Loads customer, credit bureau, transaction, product, and marketing data.
4. Joins the datasets into Customer360.
5. Applies configuration-driven filters and derived attributes.
6. Calculates customer risk scores and risk categories.
7. Ranks customers and produces reports and output datasets.

## Technology standards

- Use Java, not Scala.
- Use the existing Java and Apache Spark APIs already used in the repository.
- Follow the existing package and class structure.
- Use Maven for compilation and execution.
- Preserve the metadata-driven design.
- Avoid hardcoding business rules when the rule belongs in pipeline-config.json.
- Preserve the common customer_id relationship across all generated datasets.
- Do not silently rename existing columns.
- Use snake_case for CSV and Spark column names unless the existing file uses another convention.
- Use deterministic or controlled-random generation where the existing generator supports it.

## Schema evolution rules

When adding a column to a data asset:

1. Inspect the latest Customer Master enhancement as the reference implementation.
2. Identify every impacted file before modifying code.
3. Update the corresponding Java generator.
4. Update the generated CSV header and row generation.
5. Update pipeline-config.json dataset definitions if schemas are declared there.
6. Update Spark transformations, joins, filters, attributes, risk rules, or outputs only when the new column is required downstream.
7. Preserve data types across generator, CSV, Spark schema, configuration, and output.
8. Generate realistic and internally consistent values.
9. Prevent impossible combinations and negative financial values.
10. Preserve compatibility with existing datasets and pipeline behavior.

## Data realism rules

Generated values must have realistic relationships.

Examples:

- active_loan_count must not exceed total_loan_count.
- outstanding_loan_amount must not be negative.
- credit_utilization_pct must generally be derived from outstanding revolving credit and total credit limit when suitable source fields exist.
- missed_payments_12m must not be negative.
- maximum_dpd must be consistent with delinquency and default status.
- emi_outflow should correlate with outstanding loan amount and active loans.
- salary_credit_frequency should be consistent with employment and income characteristics where those fields are available.
- account_age_months and employment_tenure_months must not be negative.
- campaign_response_rate must remain within its selected scale.
- Boolean flags must use the format already followed by the existing datasets.

## Validation requirements

For every enhancement:

1. Compile the relevant Maven module.
2. Generate a small validation dataset first.
3. Verify CSV headers and sample records.
4. Verify row counts across related assets.
5. Verify customer_id integrity.
6. Run the Spark pipeline.
7. Confirm the new fields are present at the intended processing stage.
8. Validate null counts, ranges, and cross-field constraints.
9. Run or add tests for generation and downstream processing.
10. Summarize all modified files, assumptions, validation results, and remaining concerns.

Do not commit generated datasets containing 100K or 1M records unless the user explicitly requests it.

Do not overwrite unrelated local changes.
Do not delete existing configuration, generator logic, or tests to make a build pass.