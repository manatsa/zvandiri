package zw.org.zvandiri.business.util;

import zw.org.zvandiri.business.util.dto.SearchDTO;

public class Reportutil {

    public static final StringBuilder commonQueryString(SearchDTO dto) {
        StringBuilder builder = new StringBuilder("Select Distinct p from Cadre p  ");
        int position = 0;

        if (dto.getSearch(dto)) {
            builder.append(" where ");
            if (dto.getProvince() != null) {
                if (position == 0) {
                    builder.append("p.primaryClinic.district.province=:province");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic.district.province=:province");
                }
            }
            if (dto.getDistrict() != null) {
                if (position == 0) {
                    builder.append(" p.primaryClinic.district=:district");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic.district=:district");
                }
            }
            if (dto.getPrimaryClinic() != null) {
                if (position == 0) {
                    builder.append("p.primaryClinic=:primaryClinic");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic=:primaryClinic");
                }
            }
            if (dto.getSupportGroup() != null) {
                if (position == 0) {
                    builder.append("p.supportGroup=:supportGroup");
                    position++;
                } else {
                    builder.append(" and p.supportGroup=:supportGroup");
                }
            }
            if (dto.getGender() != null) {
                if (position == 0) {
                    builder.append("p.gender=:gender");
                    position++;
                } else {
                    builder.append(" and p.gender=:gender");
                }
            }

            if (dto.getStatus() != null) {
                if (position == 0) {
                    builder.append(" p.status=:status");
                    position++;
                } else {
                    builder.append(" and p.status=:status");
                }
            }
        }

        return builder;
    }

    public static final StringBuilder commonQueryCount(SearchDTO dto) {
        StringBuilder builder = new StringBuilder("Select count(Distinct p) from Cadre p ");
        int position = 0;

        if (dto.getSearch(dto)) {
            builder.append(" where ");
            if (dto.getProvince() != null) {
                if (position == 0) {
                    builder.append("p.primaryClinic.district.province=:province");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic.district.province=:province");
                }
            }
            if (dto.getDistrict() != null) {
                if (position == 0) {
                    builder.append(" p.primaryClinic.district=:district");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic.district=:district");
                }
            }
            if (dto.getPrimaryClinic() != null) {
                if (position == 0) {
                    builder.append("p.primaryClinic=:primaryClinic");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic=:primaryClinic");
                }
            }
            if (dto.getSupportGroup() != null) {
                if (position == 0) {
                    builder.append("p.supportGroup=:supportGroup");
                    position++;
                } else {
                    builder.append(" and p.supportGroup=:supportGroup");
                }
            }
            if (dto.getGender() != null) {
                if (position == 0) {
                    builder.append("p.gender=:gender");
                    position++;
                } else {
                    builder.append(" and p.gender=:gender");
                }
            }

            if (dto.getStatus() != null) {
                if (position == 0) {
                    builder.append(" p.status=:status");
                    position++;
                } else {
                    builder.append(" and p.status=:status");
                }
            }
        }

        return builder;
    }
}
